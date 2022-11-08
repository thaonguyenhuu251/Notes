package com.example.notes.view.home

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.App
import com.example.notes.R
import com.example.notes.adapter.WorkDoAdapter
import com.example.notes.database.WorkRoomDatabaseClass
import com.example.notes.database.WorkRoomTrashDatabase
import com.example.notes.databinding.FragmentListWorkBinding
import com.example.notes.helper.SwipeHelper
import com.example.notes.model.Work
import com.example.notes.util.Constants
import com.example.notes.util.Event
import com.example.notes.util.FileUtils
import com.example.notes.util.search
import io.reactivex.rxjava3.disposables.Disposable
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
    val second: String by lazy { arguments?.getString("search") ?: ""}
    private var disposable: Disposable? = null

    private val editWorkResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val id = result.data?.getLongExtra(Constants.WORK_ID, System.currentTimeMillis())
                val nameWork = result.data?.getStringExtra(Constants.WORK_NAME)
                val timeNotify = result.data?.getLongExtra(Constants.WORK_TIME, System.currentTimeMillis())
                val contentWork = result.data?.getStringExtra(Constants.WORK_CONTENT)
                val isNoty = result.data?.getBooleanExtra(Constants.WORK_NOTIFY, false)
                val editWork = Work(id, nameWork, contentWork, timeNotify, isNoty, false)
                lifecycleScope.launch {
                    workDatabase.updateWork(editWork)
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
        disposable = App.eventBus.subscribe{
            it[Event.EVENT_SEARCH_DOCUMENT]?.let { data ->
                (data as String?)?.let { search ->
                    workDoAdapter.submitList(listWork.filter { it.nameWork!!.contains(search)
                            || it.contentWork!!.contains(search) })
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

    }

    private fun setRecyclerView() {
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
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
                    Color.parseColor("#D3D3D3"),
                    Color.parseColor("#FFFFFF")
                ) { pos: Int ->
                    val intent = Intent(requireContext(), AddWorkActivity::class.java)
                    val workList = workDoAdapter.currentList.toMutableList()
                    intent.putExtra(Constants.WORK_NAME, workList[pos].nameWork)
                    intent.putExtra(Constants.WORK_CONTENT, workList[pos].contentWork)
                    intent.putExtra(Constants.WORK_TIME, workList[pos].timeNotify)
                    intent.putExtra(Constants.WORK_NOTIFY, workList[pos].isTimeNotify)
                    editWorkResultLauncher.launch(intent)
                })

                underlayButtons?.add(UnderlayButton(
                    "Mark",
                    AppCompatResources.getDrawable(requireContext(), R.drawable.ic_star),
                    Color.parseColor("#D3D3D3"),
                    Color.parseColor("#FFFFFF")
                ) { pos: Int ->
                    val workList = workDoAdapter.currentList.toMutableList()
                    val updateWork = workList[pos]
                    updateWork.isMark = true
                    CoroutineScope(Dispatchers.IO).launch {
                        workDatabase.updateWork(updateWork)
                    }
                    Toast.makeText(requireContext(), "Data Update Success", Toast.LENGTH_LONG).show()
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
            }
        }
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