package com.example.notes.view.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.adapter.NoteDoAdapter
import com.example.notes.databinding.FragmentListNoteBinding
import com.example.notes.helper.SwipeHelper
import com.example.notes.model.Note
import com.example.notes.util.FileUtils
import com.example.notes.view.components.DeleteDialog

class ListNoteFragment : Fragment() {
    lateinit var noteDoAdapter: NoteDoAdapter
    private var layoutManager: LinearLayoutManager? = null
    lateinit var binding: FragmentListNoteBinding
    var recyclerView: RecyclerView? = null
    private var listNote = mutableListOf<Note>()

    var posSelect :Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListNoteBinding.inflate(inflater, container, false)
        return binding.root
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
                    "  Delete  ",
                    AppCompatResources.getDrawable(
                        requireContext(), R.drawable.ic_delete_mode),
                    Color.parseColor("#DC143C"),
                    Color.parseColor("#FFFFFF")
                ) { pos: Int ->

                    val builder = Dialog(requireContext())
                    builder.setContentView(R.layout.custom_dialog_delete)
                    builder.window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT)
                    AlertDialog.Builder(context, R.style.MyDialog)

                    builder.window?.setBackgroundDrawableResource(R.drawable.bg_dialog_round)

                    val btnDelete = builder.findViewById<Button>(R.id.btnDelete)
                    val btnCancle = builder.findViewById<Button>(R.id.btnCancle)

                    btnDelete.setOnClickListener{
                        noteDoAdapter.notifyItemRemoved(pos)
                        builder.dismiss()
                    }
                    btnCancle.setOnClickListener{
                        builder.dismiss()
                    }
                    builder.show()
                })

                underlayButtons?.add(UnderlayButton(
                    "   Edit   ",
                    AppCompatResources.getDrawable(requireContext(), R.drawable.ic_mode_edit),
                    Color.parseColor("#008000"),
                    Color.parseColor("#FFFFFF")
                ) { pos: Int ->
                    //noteDoAdapter.modelList.removeAt(pos)
                    noteDoAdapter.notifyItemRemoved(pos)
                })

                underlayButtons?.add(UnderlayButton(
                    "   Mark   ",
                    AppCompatResources.getDrawable(requireContext(), R.drawable.ic_star),
                    Color.parseColor("#FF7F50"),
                    Color.parseColor("#FFFFFF")
                ) { pos: Int ->
                    //noteDoAdapter.modelList.removeAt(pos)
                    noteDoAdapter.notifyItemRemoved(pos)
                })
            }
        }

        binding.root.setOnClickListener {
            FileUtils.hideKeyboard(requireActivity())
        }
        binding.content1.setOnClickListener {
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
            listNote.add(0,Note(1,"Dọn Nhà","Phải xong trước ăn cơm...",8f))
        }

        if (listNote.size == 0 ) {
            binding.recyclerview.visibility = View.GONE
            binding.imgFile.visibility = View.VISIBLE
        } else {
            binding.recyclerview.visibility = View.VISIBLE
            binding.imgFile.visibility = View.GONE
        }

        noteDoAdapter = NoteDoAdapter(requireContext(), listNote)
        binding.recyclerview.adapter = noteDoAdapter
        noteDoAdapter.notifyDataSetChanged()
    }



    companion object {
        fun newInstance(param1: String, param2: String) =
            ListNoteFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}