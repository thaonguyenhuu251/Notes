package com.example.notes.view.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.adapter.WorkDoAdapter
import com.example.notes.databinding.FragmentListWorkBinding
import com.example.notes.model.Work
import com.example.notes.util.FileUtils

class ListWorkFragment : Fragment() {
    lateinit var workDoAdapter: WorkDoAdapter
    var layoutManager: LinearLayoutManager? = null
    lateinit var binding: FragmentListWorkBinding
    var recyclerView: RecyclerView? = null
    private var listWork = mutableListOf<Work>()
    var timeFilter: String? = null

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
        binding.recyclerview.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
        binding.recyclerview.layoutManager = layoutManager

        binding.root.setOnClickListener {
            FileUtils.hideKeyboard(requireActivity())
        }
        binding.content.setOnClickListener {
            FileUtils.hideKeyboard(requireActivity())
        }
    }

    override fun onResume() {
        super.onResume()
        generateItemWork()
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun generateItemWork() {
        //listWork = MainActivity.roomDatabaseClass.workDao().getWork()

        for (i in 0 until 20) {
            listWork.add(0,Work(1,"2","3","4",8f))
        }

        if (listWork.size == 0 ) {
            binding.recyclerview.visibility = View.GONE
            binding.imgFile.visibility = View.VISIBLE
        } else {
            binding.recyclerview.visibility = View.VISIBLE
            binding.imgFile.visibility = View.GONE
        }

        workDoAdapter = WorkDoAdapter(requireContext(), listWork)
        binding.recyclerview.adapter = workDoAdapter
        workDoAdapter.notifyDataSetChanged()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}