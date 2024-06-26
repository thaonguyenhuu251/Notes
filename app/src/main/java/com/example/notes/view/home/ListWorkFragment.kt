package com.example.notes.view.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import com.example.notes.App
import com.example.notes.R
import com.example.notes.database.WorkRoomDatabaseClass
import com.example.notes.database.WorkRoomTrashDatabase
import com.example.notes.databinding.FragmentListWorkBinding
import com.example.notes.helper.SwipeHelper
import com.example.notes.model.Alarm
import com.example.notes.model.Work
import com.example.notes.util.*
import com.example.notes.view.components.ShowResultDialog
import com.example.notes.viewmodels.CreateAlarmViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ListWorkFragment : Fragment() {
    lateinit var workDoAdapter: WorkDoAdapter
    private lateinit var binding: FragmentListWorkBinding
    var searchText: String? = null
    private var listWork = mutableListOf<Work>()
    private val workDatabase by lazy { WorkRoomDatabaseClass.getDataBase(requireContext()).workDao() }
    private val workTrashDatabase by lazy { WorkRoomTrashDatabase.getDataBase(requireContext()).workMarkDao() }
    private var createAlarmViewModel: CreateAlarmViewModel? = null
    private var disposable: io.reactivex.rxjava3.disposables.Disposable? = null

    private val editWorkResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val id = result.data?.getLongExtra(Constants.WORK_ID, System.currentTimeMillis())
                val nameWork = result.data?.getStringExtra(Constants.WORK_NAME)
                val timeNotify = result.data?.getLongExtra(Constants.WORK_TIME, System.currentTimeMillis())
                val contentWork = result.data?.getStringExtra(Constants.WORK_CONTENT)
                val isNoty = result.data?.getBooleanExtra(Constants.WORK_NOTIFY, false)
                val isMark = result.data?.getBooleanExtra(Constants.WORK_MARK, false)
                val editWork = Work(id, nameWork, contentWork, timeNotify, isNoty, isMark)
                val alarm = Alarm(
                    Random().nextInt(Int.MAX_VALUE),
                    time = timeNotify,
                    nameWork,
                    contentWork,
                    System.currentTimeMillis(),
                    started = true,
                    recurring = false,
                    isVibration = false
                )
                lifecycleScope.launch {
                    workDatabase.updateWork(editWork)
                }

                if (isNoty == true && timeNotify!= null) {
                    createAlarmViewModel?.update(alarm)
                    alarm.schedule(requireContext())
                } else {
                    alarm.cancelAlarm(requireContext())
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListWorkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createAlarmViewModel = ViewModelProviders.of(this)[CreateAlarmViewModel::class.java]
        disposable = App.eventBus.subscribe{
            it[Event.EVENT_SEARCH_DOCUMENT]?.let { data ->
                (data as String?)?.let { search ->
                    workDoAdapter.submitList(listWork.filter { it.nameWork?.lowercase()!!.contains(search.lowercase())
                            || it.contentWork?.lowercase()!!.contains(search.lowercase()) })
                }
            }

            it[Event.EVENT_SORT_NAME_AC]?.let {
                workDoAdapter.submitList(listWork.sortedBy { it.nameWork })
            }

            it[Event.EVENT_SORT_TIME_CREATE]?.let {
                workDoAdapter.submitList(listWork.sortedBy { it.idWork})
            }

            it[Event.EVENT_SORT_TIME_OPEN]?.let {
                //workDoAdapter.submitList(listWork.sortedBy { })
            }

            it[Event.EVENT_SORT_TIME_AC]?.let {
                workDoAdapter.submitList(listWork.sortedBy { it.timeNotify })
            }

            it[Event.EVENT_SORT_TIME_DC]?.let {
                workDoAdapter.submitList(listWork.sortedByDescending { it.timeNotify })
            }

            it[Event.EVENT_CHANGE_VIEW_MODE]?.let { data ->
                if (PreferencesSettings.getViewMode(requireContext()) == 0 ) {
                    binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
                } else {
                    binding.recyclerview.layoutManager = GridLayoutManager(requireContext(), 2)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        observeWorks()

        binding.root.setOnClickListener {
            FileUtils.hideKeyboard(requireActivity())
        }
        binding.content.setOnClickListener {
            FileUtils.hideKeyboard(requireActivity())
        }

        binding.recyclerview.setOnClickListener {
            FileUtils.hideKeyboard(requireActivity())
        }
    }

    override fun onResume() {
        super.onResume()
        observeWorks()
    }

    private fun setRecyclerView() {
        if (PreferencesSettings.getViewMode(requireContext()) == 0 ) {
            binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        } else {
            binding.recyclerview.layoutManager = GridLayoutManager(requireContext(), 2)
        }
        binding.recyclerview.setHasFixedSize(true)
        workDoAdapter = WorkDoAdapter()

        object : SwipeHelper(requireContext(), binding.recyclerview, false) {
            override fun instantiateUnderlayButton(
                viewHolder: RecyclerView.ViewHolder?,
                underlayButtons: MutableList<UnderlayButton>?
            ) {

                underlayButtons?.add(UnderlayButton(
                    "Delete",
                    AppCompatResources.getDrawable(
                        requireContext(), R.drawable.ic_delete_mode),
                    Color.parseColor("#DC143C"),
                    Color.parseColor("#FFFFFF")
                ) { pos: Int ->
                    val workList = workDoAdapter.currentList.toMutableList()
                    val removeNote = workList[pos]
                    CoroutineScope(Dispatchers.IO).launch {
                        workDatabase.deleteWork(removeNote)
                    }
                    lifecycleScope.launch {
                        workTrashDatabase.addWork(removeNote)
                    }
                })

                underlayButtons?.add(UnderlayButton(
                    "Edit",
                    AppCompatResources.getDrawable(requireContext(), R.drawable.ic_mode_edit),
                    Color.parseColor("#F4A460"),
                    Color.parseColor("#FFFFFF")
                ) { pos: Int ->
                    val intent = Intent(requireContext(), AddWorkActivity::class.java)
                    val workList = workDoAdapter.currentList.toMutableList()
                    intent.putExtra(Constants.WORK_ID, workList[pos].idWork)
                    intent.putExtra(Constants.WORK_NAME, workList[pos].nameWork)
                    intent.putExtra(Constants.WORK_CONTENT, workList[pos].contentWork)
                    intent.putExtra(Constants.WORK_TIME, workList[pos].timeNotify)
                    intent.putExtra(Constants.WORK_NOTIFY, workList[pos].isTimeNotify)
                    intent.putExtra(Constants.WORK_MARK, workList[pos].isMark)
                    editWorkResultLauncher.launch(intent)
                })

                underlayButtons?.add(UnderlayButton(
                    "Mark",
                    AppCompatResources.getDrawable(requireContext(), R.drawable.ic_star),
                    Color.parseColor("#F0E68C"),
                    Color.parseColor("#FFFFFF")
                ) { pos: Int ->
                    val workList = workDoAdapter.currentList.toMutableList()
                    val updateWork = workList[pos]
                    updateWork.isMark = true
                    CoroutineScope(Dispatchers.IO).launch {
                        workDatabase.updateWork(updateWork)
                    }
                    Toast.makeText(requireContext(), getString(R.string.data_update_success), Toast.LENGTH_LONG).show()
                })
            }

        }

        binding.recyclerview.adapter = workDoAdapter
    }

    private fun observeWorks() {
        lifecycleScope.launch {
            workDatabase.getWork().collect { worksList ->
                if (worksList.isNotEmpty()) {
                    binding.recyclerview.visibility = View.VISIBLE
                    binding.imgFile.visibility = View.GONE
                    listWork = worksList.toMutableList()
                    workDoAdapter.submitList(worksList)
                } else {
                    binding.recyclerview.visibility = View.GONE
                    binding.imgFile.visibility = View.VISIBLE
                }
                binding.tvNumberFile.text = worksList.size.toString() + " Records"
            }
        }
    }

    inner class WorkDoAdapter : ListAdapter<Work, WorkDoAdapter.WorkHolder>(DiffCallback()) {
        lateinit var context: Context

        inner class WorkHolder(view: View) : RecyclerView.ViewHolder(view)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.item_work, parent, false)
            context = parent.context
            return WorkHolder(v)
        }

        override fun onBindViewHolder(holder: WorkHolder, position: Int) {
            val currentItem = getItem(position)
            val txtName = holder.itemView.findViewById<TextView>(R.id.txtName)
            val txtContentWork = holder.itemView.findViewById<TextView>(R.id.txtContentWork)
            val txtTimeComplete = holder.itemView.findViewById<TextView>(R.id.txtTimeComplete)
            val imgWork = holder.itemView.findViewById<ImageView>(R.id.imgWork)

            txtName.text = context.getString(R.string.work_name, currentItem.nameWork)
            txtContentWork.text = context.getString(R.string.work_content, currentItem.contentWork)
            txtTimeComplete.text = SimpleDateFormat(context.getString(R.string.work_day)).format(currentItem.timeNotify) + " " +
                    SimpleDateFormat(context.getString(R.string.work_time)).format(currentItem.timeNotify)

            holder.itemView.setOnClickListener {
                val intent = Intent(requireContext(), AddWorkActivity::class.java)
                intent.putExtra(Constants.WORK_ID, currentItem.idWork)
                intent.putExtra(Constants.WORK_NAME, currentItem.nameWork)
                intent.putExtra(Constants.WORK_CONTENT, currentItem.contentWork)
                intent.putExtra(Constants.WORK_TIME, currentItem.timeNotify)
                intent.putExtra(Constants.WORK_NOTIFY, currentItem.isTimeNotify)
                intent.putExtra(Constants.WORK_MARK, currentItem.isMark)
                editWorkResultLauncher.launch(intent)
            }

            if (FileUtils.isPrimeNumber(position) && position % 2 == 0) {
                Glide.with(context)
                    .load("") // image url
                    .error(
                        AppCompatResources.getDrawable(
                            context,
                            R.drawable.ic_celendar_color
                        )
                    ) // any image in case of error
                    .into(imgWork)
            } else if (FileUtils.isPrimeNumber(position) && position % 2 == 1) {
                Glide.with(context)
                    .load("") // image url
                    .error(
                        AppCompatResources.getDrawable(
                            context,
                            R.drawable.ic_time_color
                        )
                    ) // any image in case of error
                    .into(imgWork)
            } else {
                Glide.with(context)
                    .load("") // image url
                    .error(
                        AppCompatResources.getDrawable(
                            context,
                            R.drawable.ic_book_color
                        )
                    ) // any image in case of error
                    .into(imgWork)
            }
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<Work>() {
        override fun areItemsTheSame(oldItem: Work, newItem: Work) =
            oldItem.idWork == newItem.idWork

        override fun areContentsTheSame(oldItem: Work, newItem: Work) =
            oldItem == newItem
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            ListWorkFragment().apply {
                arguments = Bundle().apply {
                    putString("search", param1)
                }
            }
    }

}