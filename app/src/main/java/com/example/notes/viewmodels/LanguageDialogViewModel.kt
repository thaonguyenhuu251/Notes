package com.example.notes.viewmodels

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R

class LanguageDialogViewModel(itemView: View):RecyclerView.ViewHolder(itemView) {
    var txtLanguage:TextView
    init {
        txtLanguage = itemView.findViewById(R.id.txtLanguage)
    }
}
