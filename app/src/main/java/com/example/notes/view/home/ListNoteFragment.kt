package com.example.notes.view.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import com.example.notes.App
import com.example.notes.R
import com.example.notes.adapter.NoteDoAdapter
import com.example.notes.database.NoteRoomDatabaseClass
import com.example.notes.database.NoteRoomTrashDatabase
import com.example.notes.databinding.FragmentListNoteBinding
import com.example.notes.helper.SwipeHelper
import com.example.notes.model.Note
import com.example.notes.util.*
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
        observeNotes()
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
                binding.tvNumberFile.text = notesList.size.toString() + " Records"
            }
        }
    }

    inner class NoteDoAdapter : ListAdapter<Note, NoteDoAdapter.NoteHolder>(DiffCallback())  {
        lateinit var context: Context
        inner class NoteHolder(view: View) : RecyclerView.ViewHolder(view)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteDoAdapter.NoteHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.item_add_note, parent, false)
            context = parent.context
            return NoteHolder(v)
        }

        override fun onBindViewHolder(holder: NoteHolder, position: Int) {
            val currentItem = getItem(position)
            val txtTitle = holder.itemView.findViewById<TextView>(R.id.txtTitleNote)
            val txtContext = holder.itemView.findViewById<TextView>(R.id.txtContentNote)
            val txtDate = holder.itemView.findViewById<TextView>(R.id.txtDateNote)
            val imgNote = holder.itemView.findViewById<ImageView>(R.id.ivNote)
            txtDate.text = SimpleDateFormat(context.getString(R.string.work_day)).format(currentItem.timeNotify)
            txtTitle.text = context.getString(R.string.node_name, currentItem.titleNote)
            txtContext.text = context.getString(R.string.node_content, currentItem.contentNote)

            holder.itemView.setOnClickListener {
                val intent = Intent(requireContext(), AddNoteActivity::class.java)
                val noteList = noteDoAdapter.currentList.toMutableList()
                intent.putExtra(Constants.NOTE_ID, currentItem.idNote)
                intent.putExtra(Constants.NOTE_TITLE, currentItem.titleNote)
                intent.putExtra(Constants.NOTE_CONTENT, currentItem.contentNote)
                intent.putExtra(Constants.NOTE_TIME, currentItem.timeNotify)
                intent.putExtra(Constants.NOTE_MARK, currentItem.isMark)
                editNoteResultLauncher.launch(intent)
            }

            Glide.with(context)
                .load("") // image url
                .error(
                    AppCompatResources.getDrawable(
                        context,
                        R.drawable.img_note
                    )
                ) // any image in case of error
                .into(imgNote)
        }


    }

    class DiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note) =
            oldItem.idNote == newItem.idNote

        override fun areContentsTheSame(oldItem: Note, newItem: Note) =
            oldItem == newItem
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