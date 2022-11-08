package com.example.notes.view.home

import android.annotation.SuppressLint
import android.app.Activity
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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.App
import com.example.notes.R
import com.example.notes.adapter.NoteDoAdapter
import com.example.notes.adapter.WorkDoAdapter
import com.example.notes.database.NoteRoomDatabaseClass
import com.example.notes.database.NoteRoomTrashDatabase
import com.example.notes.databinding.FragmentListNoteBinding
import com.example.notes.databinding.FragmentListWorkBinding
import com.example.notes.helper.SwipeHelper
import com.example.notes.model.Note
import com.example.notes.util.Constants
import com.example.notes.util.FileUtils
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListNoteFragment : Fragment() {
    lateinit var noteDoAdapter: NoteDoAdapter
    private lateinit var binding: FragmentListNoteBinding
    private var listNote = mutableListOf<Note>()
    private val noteDatabase by lazy { NoteRoomDatabaseClass.getDataBase(requireContext()).noteDao() }
    private val noteTrashDatabase by lazy { NoteRoomTrashDatabase.getDataBase(requireContext()).noteMarkDao() }
    val second: String by lazy { arguments?.getString("search") ?: ""}
    private var disposable: Disposable? = null

    private val editNoteResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val id = result.data?.getLongExtra(Constants.NOTE_ID, System.currentTimeMillis())
                val titleWork = result.data?.getStringExtra(Constants.NOTE_TITLE)
                val timeNotify = result.data?.getLongExtra(Constants.NOTE_TIME, System.currentTimeMillis())
                val contentNote = result.data?.getStringExtra(Constants.NOTE_CONTENT)
                val editNote = Note(id, titleWork, contentNote, timeNotify,false)
                lifecycleScope.launch {
                    noteDatabase.updateNote(editNote)
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        disposable = App.eventBus.subscribe{
//            it[Event.EVENT_SEARCH_DOCUMENT]?.let { data ->
//                (data as String?)?.let { search ->
//                    noteDoAdapter.submitList(listNote.filter { it.titleNote!!.contains(search)
//                            || it.contentNote!!.contains(search) })
//                }
//            }
//
//            it[Event.EVENT_SORT_NAME_AC]?.let {
//                noteDoAdapter.submitList(listNote.sortedBy { it.contentNote })
//            }
//
//            it[Event.EVENT_SORT_TIME_CREATE]?.let {
//                noteDoAdapter.submitList(listNote.sortedBy { it.idNote})
//            }
//
//            it[Event.EVENT_SORT_TIME_OPEN]?.let {
//                //workDoAdapter.submitList(listWork.sortedBy { })
//            }
//
//            it[Event.EVENT_SORT_TIME_AC]?.let {
//                noteDoAdapter.submitList(listNote.sortedBy { it.timeNotify })
//            }
//
//            it[Event.EVENT_SORT_TIME_DC]?.let {
//                workDoAdapter.submitList(listWork.sortedByDescending { it.timeNotify })
//            }
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        observeNotes()

        binding.root.setOnClickListener {
            FileUtils.hideKeyboard(requireActivity())
        }
        binding.content1.setOnClickListener {
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
        noteDoAdapter = NoteDoAdapter()

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
                    val noteList = noteDoAdapter.currentList.toMutableList()
                    val removeNote = noteList[pos]
                    CoroutineScope(Dispatchers.IO).launch {
                        noteDatabase.deleteNote(removeNote)
                    }
                    lifecycleScope.launch {
                        noteTrashDatabase.addNote(removeNote)
                    }
                })

                underlayButtons?.add(UnderlayButton(
                    "Edit",
                    AppCompatResources.getDrawable(requireContext(), R.drawable.ic_mode_edit),
                    Color.parseColor("#D3D3D3"),
                    Color.parseColor("#FFFFFF")
                ) { pos: Int ->
                    val intent = Intent(requireContext(), AddWorkActivity::class.java)
                    val workList = noteDoAdapter.currentList.toMutableList()
                    intent.putExtra(Constants.NOTE_TITLE, workList[pos].titleNote)
                    intent.putExtra(Constants.NOTE_CONTENT, workList[pos].contentNote)
                    intent.putExtra(Constants.NOTE_TIME, workList[pos].timeNotify)
                    editNoteResultLauncher.launch(intent)
                })

                underlayButtons?.add(UnderlayButton(
                    "Mark",
                    AppCompatResources.getDrawable(requireContext(), R.drawable.ic_star),
                    Color.parseColor("#D3D3D3"),
                    Color.parseColor("#FFFFFF")
                ) { pos: Int ->
                    val noteList = noteDoAdapter.currentList.toMutableList()
                    val updateNote = noteList[pos]
                    updateNote.isMark = true
                    CoroutineScope(Dispatchers.IO).launch {
                        noteDatabase.updateNote(updateNote)
                    }
                    Toast.makeText(requireContext(), "Data Update Success", Toast.LENGTH_LONG).show()
                })
            }

        }

        binding.recyclerview.adapter = noteDoAdapter
    }

    private fun observeNotes() {
        lifecycleScope.launch {
            noteDatabase.getNotes().collect { notesList ->
                if (notesList.isNotEmpty()) {
                    binding.recyclerview.visibility = View.VISIBLE
                    binding.imgFile.visibility = View.GONE
                    listNote = notesList.toMutableList()
                    noteDoAdapter.submitList(notesList)
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
            ListNoteFragment().apply {
                arguments = Bundle().apply {
                    putString("search", param1)
                }
            }
    }
}