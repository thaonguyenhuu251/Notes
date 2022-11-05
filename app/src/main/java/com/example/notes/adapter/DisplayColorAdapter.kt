package com.example.notes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.util.Constants
import com.example.notes.util.Methods
import com.example.notes.util.PreferencesSettings

class DisplayColorAdapter : RecyclerView.Adapter<DisplayColorAdapter.DisplayColorViewModel> {
    lateinit var context: Context
    lateinit var listDarkColor:List<Int>
    lateinit var listLightColor:List<Int>
    lateinit var listTextColor:List<String>

    constructor(
        context: Context,
        listDarkColor: List<Int>,
        listLightColor: List<Int>,
        listTextColor: List<String>
    ) : super() {
        this.context = context
        this.listDarkColor = listDarkColor
        this.listLightColor = listLightColor
        this.listTextColor = listTextColor
    }

    constructor(
        listDarkColor: List<Int>,
        listLightColor: List<Int>,
        listTextColor: List<String>
    ) : super() {
        this.listDarkColor = listDarkColor
        this.listLightColor = listLightColor
        this.listTextColor = listTextColor
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisplayColorViewModel {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.item_color, parent, false)
        return DisplayColorViewModel(context, itemView)
    }

    override fun getItemCount(): Int {
        return listDarkColor.size
    }

    override fun onBindViewHolder(holder: DisplayColorViewModel, position: Int) {
        holder.ivDarkColor.setColorFilter(ContextCompat.getColor(context, listDarkColor[position]))
        holder.ivLightColor.setColorFilter(ContextCompat.getColor(context, listLightColor[position]))
        holder.txtColor.setText(listTextColor[position])
    }

    inner class DisplayColorViewModel(context: Context, itemView: View): RecyclerView.ViewHolder(itemView)  {
        var ivDarkColor: ImageView
        var ivLightColor: ImageView
        var txtColor: TextView

        init {
            ivDarkColor = itemView.findViewById(R.id.ivDarkColor)
            ivLightColor = itemView.findViewById(R.id.ivLightColor)
            txtColor = itemView.findViewById(R.id.txtColor)

            itemView.setOnClickListener {
                PreferencesSettings.saveToBackground(context, Methods.getColorTheme(Constants.COLOR_BLUE))
            }
        }
    }
}