package com.example.notes.viewmodels

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.google.android.material.imageview.ShapeableImageView

class DisplayBackgroundViewModel(itemView: View):RecyclerView.ViewHolder(itemView) {
    var ivshapeable: ShapeableImageView

    init {
        ivshapeable = itemView.findViewById<ShapeableImageView>(R.id.ivshapeable)
    }
}