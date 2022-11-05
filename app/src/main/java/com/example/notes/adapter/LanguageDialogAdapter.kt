package com.example.notes.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.util.Constants
import com.example.notes.util.PreferencesSettings
import com.example.notes.viewmodels.LanguageDialogViewModel

class LanguageDialogAdapter:RecyclerView.Adapter<LanguageDialogViewModel> {
    lateinit var context:Context
    var txtLanguage:TextView? = null
    var listTextLanguage = mutableListOf<Int>()
    var listDrawableLanguage = mutableListOf<Int>()
    var i:Int = 1

    constructor(
        context: Context,
        txtLanguage: TextView?,
        listTextLanguage: MutableList<Int>,
        listDrawableLanguage: MutableList<Int>
    ) : super() {
        this.context = context
        this.txtLanguage = txtLanguage
        this.listTextLanguage = listTextLanguage
        this.listDrawableLanguage = listDrawableLanguage
    }

    constructor(
        context: Context,
        listTextLanguage: MutableList<Int>,
        listDrawableLanguage: MutableList<Int>
    ) : super() {
        this.context = context
        this.listTextLanguage = listTextLanguage
        this.listDrawableLanguage = listDrawableLanguage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageDialogViewModel {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.item_language, parent, false)
        return LanguageDialogViewModel(itemView)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: LanguageDialogViewModel, position: Int) {
        holder.txtLanguage.setText(listTextLanguage[position])
        holder.txtLanguage.setCompoundDrawablesWithIntrinsicBounds(listDrawableLanguage[position], 0, 0, 0)
        holder.txtLanguage.setOnClickListener {
            holder.txtLanguage.setCompoundDrawablesRelativeWithIntrinsicBounds(listDrawableLanguage[position], 0, R.drawable.ic_choose_tick, 0)
            i = position
            notifyDataSetChanged()
        }
        if(i == position){
            PreferencesSettings.setLanguage(context, Constants.LG_VIETNAM)
            holder.txtLanguage.setCompoundDrawablesRelativeWithIntrinsicBounds(listDrawableLanguage[position], 0, R.drawable.ic_choose_tick, 0)
        }
        else{
            holder.txtLanguage.setCompoundDrawablesWithIntrinsicBounds(listDrawableLanguage[position], 0, 0, 0)
        }
    }

    override fun getItemCount(): Int {
        return listTextLanguage.size
    }

}