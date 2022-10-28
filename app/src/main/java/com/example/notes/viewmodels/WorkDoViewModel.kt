package com.example.notes.viewmodels

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R

class WorkDoViewModel(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var txtName: TextView
    var txtStartDay: TextView
    var txtTimeComplete: TextView
    var imgWork: ImageView

    init {
        txtName = itemView.findViewById<TextView>(R.id.txtName)
        txtStartDay = itemView.findViewById<TextView>(R.id.txtDayStart)
        txtTimeComplete = itemView.findViewById<TextView>(R.id.txtTimeComplete)
        imgWork = itemView.findViewById<ImageView>(R.id.imgWork)
    }
}