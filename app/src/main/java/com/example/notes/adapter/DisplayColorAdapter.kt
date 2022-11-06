package com.example.notes.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.model.DisplayColor
import com.example.notes.util.Constants
import com.example.notes.util.Methods
import com.example.notes.util.PreferencesSettings
import com.example.notes.view.home.MainActivity

class DisplayColorAdapter : RecyclerView.Adapter<DisplayColorAdapter.DisplayColorViewModel> {
    lateinit var context: Context
    var listColor: List<DisplayColor>

    constructor(context: Context, listColor: List<DisplayColor>) : super() {
        this.context = context
        this.listColor = listColor
    }

    constructor(listColor: List<DisplayColor>) : super() {
        this.listColor = listColor
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisplayColorViewModel {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.item_color, parent, false)
        return DisplayColorViewModel(context, itemView)
    }

    override fun getItemCount(): Int {
        return listColor.size
    }

    override fun onBindViewHolder(holder: DisplayColorViewModel, position: Int) {
        holder.bindItem(listColor[position])
    }

    inner class DisplayColorViewModel(context: Context, itemView: View): RecyclerView.ViewHolder(itemView)  {
        var ivDarkColor: ImageView = itemView.findViewById(R.id.ivDarkColor)
        var ivLightColor: ImageView = itemView.findViewById(R.id.ivLightColor)
        var txtColor: TextView = itemView.findViewById(R.id.txtColor)
        fun bindItem(listColor: DisplayColor) {

            ivDarkColor.setColorFilter(ContextCompat.getColor(context, listColor.darkColor))
            ivLightColor.setColorFilter(ContextCompat.getColor(context, listColor.lightColor))
            txtColor.text = listColor.textColor

            itemView.setOnClickListener {
                PreferencesSettings.saveToBackground(
                    context,
                    Methods.getColorTheme(Methods.getStringColor(listColor.textColor)))

                PreferencesSettings.saveToColor(
                    context,
                    Methods.getColorDark(Methods.getStringColor(listColor.textColor)))

                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            }
        }

    }
}