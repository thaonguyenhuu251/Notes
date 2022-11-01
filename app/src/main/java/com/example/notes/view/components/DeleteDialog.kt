package com.example.notes.view.components

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.notes.R
import com.example.notes.adapter.NoteDoAdapter
import com.example.notes.databinding.CustomDialogDeleteBinding
import com.example.notes.model.Note
import com.example.notes.view.home.ListNoteFragment

class DeleteDialog : DialogFragment() {
    private lateinit var binding: CustomDialogDeleteBinding

    lateinit var noteDoAdapter: NoteDoAdapter
    lateinit var listNoteFragment: ListNoteFragment
    private var listNote = mutableListOf<Note>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CustomDialogDeleteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dialog = Dialog(requireContext())
        val window = dialog.window
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        setStyle(STYLE_NORMAL, R.style.MyDialog)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}