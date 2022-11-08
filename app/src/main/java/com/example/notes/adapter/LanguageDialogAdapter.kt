package com.example.notes.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.util.Constants
import com.example.notes.util.Event
import com.example.notes.util.Methods
import com.example.notes.util.PreferencesSettings
import com.example.notes.view.components.LanguageDialog
import com.example.notes.view.home.MainActivity

class LanguageDialogAdapter : RecyclerView.Adapter<LanguageDialogAdapter.LanguageDialogViewModel> {
    lateinit var context: Context
    var listLanguage = mutableListOf<String>()

    constructor(context: Context, listLanguage: MutableList<String>) : super() {
        this.context = context
        this.listLanguage = listLanguage
    }

    constructor(listLanguage: MutableList<String>) : super() {
        this.listLanguage = listLanguage
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageDialogViewModel {
        val itemView: View =
            LayoutInflater.from(context).inflate(R.layout.item_language, parent, false)
        return LanguageDialogViewModel(context, itemView)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: LanguageDialogViewModel, position: Int) {
        holder.bindItem(listLanguage[position])
    }

    inner class LanguageDialogViewModel(context: Context, itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private var txtLanguage: TextView = itemView.findViewById(R.id.txtLanguage)
        @SuppressLint("NotifyDataSetChanged")
        fun bindItem(listLanguage: String) {
            txtLanguage.text = Methods.getStringLanguage(listLanguage)
            txtLanguage.setCompoundDrawablesWithIntrinsicBounds(
                Methods.getLanguages(listLanguage),
                0,
                0,
                0
            )

            itemView.setOnClickListener {
                txtLanguage.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    Methods.getLanguages(listLanguage),
                    0,
                    R.drawable.ic_choose_tick,
                    0
                )

                Event.eventChangeLanguage()

                PreferencesSettings.setLanguage(context, listLanguage)
                notifyDataSetChanged()
            }

            if (listLanguage == PreferencesSettings.getLanguage(context)) {
                txtLanguage.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    Methods.getLanguages(listLanguage),
                    0,
                    R.drawable.ic_choose_tick,
                    0
                )
            } else {
                txtLanguage.setCompoundDrawablesWithIntrinsicBounds(
                    Methods.getLanguages(listLanguage),
                    0,
                    0,
                    0
                )
            }
        }

    }

    override fun getItemCount(): Int {
        return listLanguage.size
    }

}

