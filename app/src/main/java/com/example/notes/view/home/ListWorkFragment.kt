package com.example.notes.view.home

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.adapter.WorkDoAdapter
import com.example.notes.database.WorkRoomDatabaseClass
import com.example.notes.database.WorkRoomTrashDatabase
import com.example.notes.databinding.FragmentListWorkBinding
import com.example.notes.helper.SwipeHelper
import com.example.notes.model.Work
import com.example.notes.util.FileUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListWorkFragment : Fragment() {
    lateinit var workDoAdapter: WorkDoAdapter
    private lateinit var binding: FragmentListWorkBinding

    private val workDatabase by lazy { WorkRoomDatabaseClass.getDataBase(requireContext()).workDao() }
    private val workTrashDatabase by lazy { WorkRoomTrashDatabase.getDataBase(requireContext()).workMarkDao() }

    val editWorkResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val id = result.data?.getLongExtra("idWork", 0L)
                val nameWork = result.data?.getStringExtra("nameWork")
                val timeComplete = result.data?.getFloatExtra("timeComplete", 0f)
                val startDay = result.data?.getStringExtra("startDay")
                val contentWork = result.data?.getStringExtra("contentWork")

                val editWork = Work(id, nameWork, contentWork, startDay, timeComplete!!)
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
                    val notesList = workDoAdapter.currentList.toMutableList()
                    intent.putExtra("idWork", notesList[pos].idWork)
                    intent.putExtra("nameWork", notesList[pos].nameWork)
                    intent.putExtra("timeComplete", notesList[pos].timeComplete)
                    intent.putExtra("startDay", notesList[pos].startDay)
                    intent.putExtra("contentWork", notesList[pos].contentWork)
                    editWorkResultLauncher.launch(intent)
                })

                underlayButtons?.add(UnderlayButton(
                    "Mark",
                    AppCompatResources.getDrawable(requireContext(), R.drawable.ic_star),
                    Color.parseColor("#D3D3D3"),
                    Color.parseColor("#FFFFFF")
                ) { pos: Int ->
                    val workList = workDoAdapter.currentList.toMutableList()
                    val addWork = workList[pos]
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
                    workDoAdapter.submitList(worksList)
                } else {
                    binding.recyclerview.visibility = View.GONE
                    binding.imgFile.visibility = View.VISIBLE
                }
            }
        }
    }

}