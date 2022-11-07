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
import com.example.notes.util.Methods
import com.example.notes.util.PreferencesSettings
import com.example.notes.view.home.MainActivity

class DisplayColorAdapter : RecyclerView.Adapter<DisplayColorAdapter.DisplayColorViewModel> {
    lateinit var context: Context
    var listColor: List<String>

    constructor(context: Context, listColor: List<String>) : super() {
        this.context = context
        this.listColor = listColor
    }

    constructor(listColor: List<String>) : super() {
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
        fun bindItem(listColor: String) {

            ivDarkColor.setColorFilter(ContextCompat.getColor(context, Methods.getColorDark(listColor)))
            ivLightColor.setColorFilter(ContextCompat.getColor(context, Methods.getColorLight(listColor)))
            txtColor.text = Methods.getColorString(listColor)

            itemView.setOnClickListener {
                PreferencesSettings.saveToBackground(
                    context,
                    Methods.getColorTheme(listColor))

                PreferencesSettings.saveToColor(
                    context,
                    Methods.getColorDark(listColor))

                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            }
        }

    }
}