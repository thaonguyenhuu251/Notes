package com.example.notes.view.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.adapter.WorkDoAdapter
import com.example.notes.databinding.FragmentListWorkBinding
import com.example.notes.helper.SwipeHelper
import com.example.notes.model.Work
import com.example.notes.util.FileUtils
import com.example.notes.view.components.DateDialog

class ListWorkFragment : Fragment() {
    lateinit var workDoAdapter: WorkDoAdapter
    private var layoutManager: LinearLayoutManager? = null
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
                    //workDoAdapter.modelList.removeAt(pos)
                    workDoAdapter.notifyItemRemoved(pos)

                })

                underlayButtons?.add(UnderlayButton(
                    "Edit",
                    AppCompatResources.getDrawable(requireContext(), R.drawable.ic_mode_edit),
                    Color.parseColor("#D3D3D3"),
                    Color.parseColor("#FFFFFF")
                ) { pos: Int ->
                    //workDoAdapter.modelList.removeAt(pos)
                    workDoAdapter.notifyItemRemoved(pos)

                })

                underlayButtons?.add(UnderlayButton(
                    "Mark",
                    AppCompatResources.getDrawable(requireContext(), R.drawable.ic_star),
                    Color.parseColor("#D3D3D3"),
                    Color.parseColor("#FFFFFF")
                ) { pos: Int ->
                    //workDoAdapter.modelList.removeAt(pos)
                    workDoAdapter.notifyItemRemoved(pos)

                })
            }
        }

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