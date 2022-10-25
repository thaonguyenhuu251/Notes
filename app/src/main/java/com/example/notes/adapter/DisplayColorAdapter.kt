package com.example.notes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.viewmodels.DisplayBackgroundViewModel
import com.example.notes.viewmodels.DisplayColorViewModel

class DisplayColorAdapter : RecyclerView.Adapter<DisplayColorViewModel> {
    lateinit var context: Context
    lateinit var listDarkColor:List<Int>
    lateinit var listLightColor:List<Int>
    lateinit var listTextColor:List<Int>

    constructor(
        context: Context,
        listDarkColor: List<Int>,
        listLightColor: List<Int>,
        listTextColor: List<Int>
    ) : super() {
        this.context = context
        this.listDarkColor = listDarkColor
        this.listLightColor = listLightColor
        this.listTextColor = listTextColor
    }

    constructor(
        listDarkColor: List<Int>,
        listLightColor: List<Int>,
        listTextColor: List<Int>
    ) : super() {
        this.listDarkColor = listDarkColor
        this.listLightColor = listLightColor
        this.listTextColor = listTextColor
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisplayColorViewModel {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.item_color, parent, false)
        return DisplayColorViewModel(itemView)
    }

    override fun getItemCount(): Int {
        return listDarkColor.size
    }

    override fun onBindViewHolder(holder: DisplayColorViewModel, position: Int) {
        holder.ivDarkColor.setColorFilter(ContextCompat.getColor(context, listDarkColor[position]))
        holder.ivLightColor.setColorFilter(ContextCompat.getColor(context, listLightColor[position]))
        holder.txtColor.setText(listTextColor[position])
    }
}