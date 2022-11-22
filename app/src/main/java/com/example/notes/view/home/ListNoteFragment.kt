package com.example.notes.view.home

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.App
import com.example.notes.R
import com.example.notes.adapter.NoteDoAdapter
import com.example.notes.database.NoteRoomDatabaseClass
import com.example.notes.database.NoteRoomTrashDatabase
import com.example.notes.databinding.FragmentListNoteBinding
import com.example.notes.helper.SwipeHelper
import com.example.notes.model.Note
import com.example.notes.util.Constants
import com.example.notes.util.Event
import com.example.notes.util.FileUtils
import com.example.notes.util.PreferencesSettings
import com.example.notes.viewmodels.CreateAlarmViewModel
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListNoteFragment : Fragment() {
    lateinit var noteDoAdapter: NoteDoAdapter
    private lateinit var binding: FragmentListNoteBinding
    private var listNote = mutableListOf<Note>()
    private val noteDatabase by lazy {
        NoteRoomDatabaseClass.getDataBase(requireContext()).noteDao()
    }
    private val noteTrashDatabase by lazy {
        NoteRoomTrashDatabase.getDataBase(requireContext()).noteMarkDao()
    }
    val second: String by lazy { arguments?.getString("search") ?: "" }
    private var disposable: Disposable? = null

    private val editNoteResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val id = result.data?.getLongExtra(Constants.NOTE_ID, System.currentTimeMillis())
                val titleNote = result.data?.getStringExtra(Constants.NOTE_TITLE)
                val timeNotify =
                    result.data?.getLongExtra(Constants.NOTE_TIME, System.currentTimeMillis())
                val contentNote = result.data?.getStringExtra(Constants.NOTE_CONTENT)
                val isMark = result.data?.getBooleanExtra(Constants.NOTE_MARK, false)
                val editNote = Note(id, titleNote, contentNote, timeNotify, isMark)
                lifecycleScope.launch(Dispatchers.IO) {
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

        //search
        disposable = App.eventBus.subscribe{
            it[Event.EVENT_SEARCH_DOCUMENT]?.let { data ->
                (data as String?)?.let { search ->
                    noteDoAdapter.submitList(listNote.filter { it.titleNote?.lowercase()!!.contains(search.lowercase())
                            || it.contentNote?.lowercase()!!.contains(search.lowercase()) })
                }
            }

            it[Event.EVENT_SORT_NAME_AC]?.let {
                noteDoAdapter.submitList(listNote.sortedBy{ it.titleNote?.lowercase()})
            }

            it[Event.EVENT_SORT_TIME_CREATE]?.let {
                noteDoAdapter.submitList(listNote.sortedBy { it.idNote})
            }

            it[Event.EVENT_SORT_TIME_OPEN]?.let {
                //workDoAdapter.submitList(listWork.sortedBy { })
            }

            it[Event.EVENT_SORT_TIME_AC]?.let {
                noteDoAdapter.submitList(listNote.sortedBy { it.timeNotify })
            }

            it[Event.EVENT_SORT_TIME_DC]?.let {
                noteDoAdapter.submitList(listNote.sortedByDescending { it.timeNotify })
            }

            it[Event.EVENT_CHANGE_VIEW_MODE]?.let { data ->
                if (PreferencesSettings.getViewMode(requireContext()) == 0 ) {
                    binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
                } else {
                    binding.recyclerview.layoutManager = GridLayoutManager(requireContext(), 2)
                }
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
                        requireContext(), R.drawable.ic_delete_mode
                    ),
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
                    Color.parseColor("#F4A460"),
                    Color.parseColor("#FFFFFF")
                ) { pos: Int ->
                    val intent = Intent(requireContext(), AddNoteActivity::class.java)
                    val noteList = noteDoAdapter.currentList.toMutableList()
                    intent.putExtra(Constants.NOTE_ID, noteList[pos].idNote)
                    intent.putExtra(Constants.NOTE_TITLE, noteList[pos].titleNote)
                    intent.putExtra(Constants.NOTE_CONTENT, noteList[pos].contentNote)
                    intent.putExtra(Constants.NOTE_TIME, noteList[pos].timeNotify)
                    intent.putExtra(Constants.NOTE_MARK, noteList[pos].isMark)
                    editNoteResultLauncher.launch(intent)
                })

                underlayButtons?.add(UnderlayButton(
                    "Mark",
                    AppCompatResources.getDrawable(requireContext(), R.drawable.ic_star),
                    Color.parseColor("#F0E68C"),
                    Color.parseColor("#FFFFFF")
                ) { pos: Int ->
                    val noteList = noteDoAdapter.currentList.toMutableList()
                    val updateNote = noteList[pos]
                    updateNote.isMark = true
                    CoroutineScope(Dispatchers.IO).launch {
                        noteDatabase.updateNote(updateNote)
                    }
                    Toast.makeText(requireContext(), "Data Update Success", Toast.LENGTH_LONG)
                        .show()
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