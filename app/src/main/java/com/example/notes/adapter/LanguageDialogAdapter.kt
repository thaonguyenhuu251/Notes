package com.example.notes.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.model.Language
import com.example.notes.util.Constants
import com.example.notes.util.PreferencesSettings
import com.example.notes.viewmodels.LanguageDialogViewModel

class LanguageDialogAdapter : RecyclerView.Adapter<LanguageDialogViewModel> {
    lateinit var context: Context
    var i: Int = 1

    var listLanguage = mutableListOf<Language>()

    constructor(context: Context, listLanguage: MutableList<Language>) : super() {
        this.context = context
        this.listLanguage = listLanguage
    }

    constructor(listLanguage: MutableList<Language>) : super() {
        this.listLanguage = listLanguage
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageDialogViewModel {
        val itemView: View =
            LayoutInflater.from(context).inflate(R.layout.item_language, parent, false)
        return LanguageDialogViewModel(itemView)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: LanguageDialogViewModel, position: Int) {
        holder.txtLanguage.setText(listLanguage[position].textLanguage)
        holder.txtLanguage.setCompoundDrawablesWithIntrinsicBounds(
            listLanguage[position].drawableLanguage,
            0,
            0,
            0
        )
        holder.txtLanguage.setOnClickListener {
            holder.txtLanguage.setCompoundDrawablesRelativeWithIntrinsicBounds(
                listLanguage[position].drawableLanguage,
                0,
                R.drawable.ic_choose_tick,
                0
            )
            i = position
            notifyDataSetChanged()
        }
        if (i == position) {
            PreferencesSettings.setLanguage(context, Constants.LG_VIETNAM)
            holder.txtLanguage.setCompoundDrawablesRelativeWithIntrinsicBounds(
                listLanguage[position].drawableLanguage,
                0,
                R.drawable.ic_choose_tick,
                0
            )
        } else {
            holder.txtLanguage.setCompoundDrawablesWithIntrinsicBounds(
                listLanguage[position].drawableLanguage,
                0,
                0,
                0
            )
        }
    }

    override fun getItemCount(): Int {
        return listLanguage.size
    }

}