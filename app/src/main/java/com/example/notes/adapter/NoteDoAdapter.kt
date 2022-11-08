package com.example.notes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.notes.R
import com.example.notes.model.Note
import com.example.notes.model.Work
import com.example.notes.util.FileUtils
import com.example.notes.util.SimpleDateFormat
import com.example.notes.viewmodels.NoteDoViewModel

class NoteDoAdapter : ListAdapter<Note, NoteDoAdapter.NoteHolder>(DiffCallback())  {
    lateinit var context: Context
    class NoteHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteDoAdapter.NoteHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_add_note, parent, false)
        context = parent.context
        return NoteDoAdapter.NoteHolder(v)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val currentItem = getItem(position)
        val txtTitle = holder.itemView.findViewById<TextView>(R.id.txtTitleNote)
        val txtContext = holder.itemView.findViewById<TextView>(R.id.txtContentNote)
        val txtDate = holder.itemView.findViewById<TextView>(R.id.txtDateNote)
        val imgNote = holder.itemView.findViewById<ImageView>(R.id.ivNote)
        txtDate.text = SimpleDateFormat(context.getString(R.string.work_day)).format(currentItem.timeNotify)

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

    class DiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note) =
            oldItem.idNote == newItem.idNote

        override fun areContentsTheSame(oldItem: Note, newItem: Note) =
            oldItem == newItem
    }
}