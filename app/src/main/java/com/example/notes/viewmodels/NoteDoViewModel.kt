package com.example.notes.viewmodels

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R

class NoteDoViewModel(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var txtTitle: TextView
    var txtContent: TextView
    var txtTime: TextView
    var imgNote: ImageView

    init {
        txtTitle = itemView.findViewById<TextView>(R.id.txtTitleNote)
        txtContent = itemView.findViewById<TextView>(R.id.txtContentNote)
        txtTime = itemView.findViewById<TextView>(R.id.txtDateNote)
        imgNote = itemView.findViewById<ImageView>(R.id.ivNote)
    }
}