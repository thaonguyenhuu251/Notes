package com.example.notes.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.notes.R
import com.example.notes.model.Note
import com.example.notes.model.Work
import com.example.notes.util.FileUtils
import com.example.notes.viewmodels.NoteDoViewModel
import com.example.notes.viewmodels.WorkDoViewModel

class NoteDoAdapter : RecyclerView.Adapter<NoteDoViewModel> {
    lateinit var context: Context
    var listNote: List<Note>
    var titleNote: String? = null
    var contentNote: String? = null
    var timeNote = 0f
    var idNote: Long = 0

    constructor(context: Context, listNote: List<Note>) : super() {
        this.context = context
        this.listNote = listNote
    }

    constructor(listNote: List<Note>) {
        this.listNote = listNote
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteDoViewModel {
        val itemView: View =
            LayoutInflater.from(context).inflate(R.layout.custom_item_add_note, parent, false)
        return NoteDoViewModel(itemView)
    }

    override fun getItemCount(): Int {
        return listNote.size
    }

    fun filterList(filterlist: ArrayList<Note>) {
        listNote = filterlist
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: NoteDoViewModel, position: Int) {
        holder.txtTitle.text =listNote[position].title
        holder.txtContent.text =listNote[position].content
        holder.txtTime.text = FileUtils.formatTime(listNote[position].date)
        holder.itemView.setOnClickListener {
            titleNote = listNote[position].title
            contentNote = listNote[position].content
            timeNote = listNote[position].date
            idNote = listNote[position].id
        }
        if (FileUtils.isPrimeNumber(position) && position % 2 == 0) {
            Glide.with(context)
                .load("") // image url
                .error(
                    AppCompatResources.getDrawable(
                        context,
                        R.drawable.img_note
                    )
                ) // any image in case of error
                .into(holder.imgNote)
        } else if (FileUtils.isPrimeNumber(position) && position % 2 == 1) {
            Glide.with(context)
                .load("") // image url
                .error(
                    AppCompatResources.getDrawable(
                        context,
                        R.drawable.img_note
                    )
                ) // any image in case of error
                .into(holder.imgNote)
        } else {
            Glide.with(context)
                .load("") // image url
                .error(
                    AppCompatResources.getDrawable(
                        context,
                        R.drawable.img_note
                    )
                ) // any image in case of error
                .into(holder.imgNote)
        }

    }


}