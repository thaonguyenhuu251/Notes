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
import com.example.notes.databinding.FragmentNoteMarkBinding
import com.example.notes.helper.SwipeHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteMarkFragment : Fragment() {
    lateinit var noteDoAdapter: NoteDoAdapter;
    private lateinit var binding: FragmentNoteMarkBinding

    private val noteDatabase by lazy { NoteRoomDatabaseClass.getDataBase(requireContext()).noteDao() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteMarkBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        observeWorks()
    }

    private fun observeWorks() {
        lifecycleScope.launch {
            noteDatabase.getNotes().collect { notesList ->
                if (notesList.isNotEmpty()) {
                    binding.recyclerview.visibility = View.VISIBLE
                    binding.imgFile.visibility = View.GONE
                    noteDoAdapter.submitList(notesList.filter { it.isMark == true })
                } else {
                    binding.recyclerview.visibility = View.GONE
                    binding.imgFile.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setRecyclerView() {
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.setHasFixedSize(true)
        noteDoAdapter = NoteDoAdapter()

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
                    val noteList = noteDoAdapter.currentList.toMutableList()
                    val updateNote = noteList[pos]
                    updateNote.isMark = false
                    CoroutineScope(Dispatchers.IO).launch {
                        noteDatabase.updateNote(updateNote)
                    }
                    noteDoAdapter.notifyItemChanged(pos)
                })
            }

        }
        binding.recyclerview.adapter = noteDoAdapter
    }
}



