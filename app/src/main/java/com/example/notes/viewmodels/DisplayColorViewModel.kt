package com.example.notes.viewmodels

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R

class DisplayColorViewModel(itemView: View): RecyclerView.ViewHolder(itemView)  {
    var ivDarkColor: ImageView
    var ivLightColor: ImageView
    var txtColor: TextView
    init {
        ivDarkColor = itemView.findViewById(R.id.ivDarkColor)
        ivLightColor = itemView.findViewById(R.id.ivLightColor)
        txtColor = itemView.findViewById(R.id.txtColor)
    }
}