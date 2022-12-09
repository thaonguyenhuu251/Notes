package com.example.notes.view.home

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.adapter.WorkDoAdapter
import com.example.notes.database.WorkRoomDatabaseClass
import com.example.notes.databinding.FragmentWorkMarkBinding
import com.example.notes.helper.SwipeHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WorkMarkFragment : Fragment() {
    lateinit var workDoAdapter: WorkDoAdapter
    private lateinit var binding: FragmentWorkMarkBinding

    private val workDatabase by lazy { WorkRoomDatabaseClass.getDataBase(requireContext()).workDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkMarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        observeWorks()
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
                    "Mark",
                    AppCompatResources.getDrawable(requireContext(), R.drawable.ic_star),
                    Color.parseColor("#FFAE21"),
                    Color.parseColor("#F7F72D")
                ) { pos: Int ->
                    val workList = workDoAdapter.currentList.toMutableList()
                    val updateWork = workList[pos]
                    updateWork.isMark = false
                    CoroutineScope(Dispatchers.IO).launch {
                        workDatabase.updateWork(updateWork)
                    }
                    workDoAdapter.notifyItemChanged(pos)
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
                    workDoAdapter.submitList(worksList.filter { it.isMark == true })
                } else {
                    binding.recyclerview.visibility = View.GONE
                    binding.imgFile.visibility = View.VISIBLE
                }
            }
        }
    }
}