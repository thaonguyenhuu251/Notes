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
import com.example.notes.adapter.NoteDoAdapter
import com.example.notes.adapter.WorkDoAdapter
import com.example.notes.database.NoteRoomDatabaseClass
import com.example.notes.database.NoteRoomTrashDatabase
import com.example.notes.database.WorkRoomDatabaseClass
import com.example.notes.database.WorkRoomTrashDatabase
import com.example.notes.databinding.FragmentNoteMarkBinding
import com.example.notes.databinding.FragmentNoteTrashBinding
import com.example.notes.databinding.FragmentWorkTrashBinding
import com.example.notes.helper.SwipeHelper
import com.example.notes.model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteTrashFragment : Fragment() {
    lateinit var noteDoAdapter: NoteDoAdapter
    private lateinit var binding: FragmentNoteTrashBinding

    private val noteDatabase by lazy { NoteRoomDatabaseClass.getDataBase(requireContext()).noteDao() }
    private val noteTrash by lazy { NoteRoomTrashDatabase.getDataBase(requireContext()).noteMarkDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
     binding = FragmentNoteTrashBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        observeWorks()
    }

    private fun observeWorks() {
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.setHasFixedSize(true)
        noteDoAdapter = NoteDoAdapter()

        object : SwipeHelper(requireContext(), binding.recyclerview, false) {
            override fun instantiateUnderlayButton(
                viewHolder: RecyclerView.ViewHolder?,
                underlayButtons: MutableList<UnderlayButton>?
            ) {
                underlayButtons?.add(UnderlayButton(
                    "Undo",
                    AppCompatResources.getDrawable(requireContext(), R.drawable.ic_undo_note),
                    Color.parseColor("#ADFF2F"),
                    Color.parseColor("#FFFFFF")
                ) { pos: Int ->
                    val noteList = noteDoAdapter.currentList.toMutableList()
                    val removeNote = noteList[pos]
                    CoroutineScope(Dispatchers.IO).launch {
                        noteTrash.deleteNote(removeNote)
                    }
                    lifecycleScope.launch {
                        noteDatabase.addNote(removeNote)
                    }
                })
            }

        }

        binding.recyclerview.adapter = noteDoAdapter
    }

    private fun setRecyclerView() {
        lifecycleScope.launch {
            noteTrash.getNote().collect { notesList ->
                if (notesList.isNotEmpty()) {
                    binding.recyclerview.visibility = View.VISIBLE
                    binding.imgFile.visibility = View.GONE
                    noteDoAdapter.submitList(notesList)
                } else {
                    binding.recyclerview.visibility = View.GONE
                    binding.imgFile.visibility = View.VISIBLE
                }
            }
        }
    }
}