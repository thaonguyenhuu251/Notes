package com.example.notes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.notes.R
import com.example.notes.util.FileUtils
import com.example.notes.viewmodels.DisplayBackgroundViewModel
import com.google.android.material.imageview.ShapeableImageView

class DisplayBackgroundAdapter :RecyclerView.Adapter<DisplayBackgroundViewModel> {
    lateinit var context:Context
    var listBackground:List<Int>

    constructor(context: Context,listBackground: List<Int>){
        this.context = context
        this.listBackground = listBackground
    }
    constructor(listBackground: List<Int>){
        this.listBackground = listBackground
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisplayBackgroundViewModel {
        val itemView:View = LayoutInflater.from(context).inflate(R.layout.item_background, parent, false)
        return DisplayBackgroundViewModel(itemView)
    }

    override fun getItemCount(): Int {
        return listBackground.size
    }

    override fun onBindViewHolder(holder: DisplayBackgroundViewModel, position: Int) {
        Glide.with(context)
            .load("")
            .error(AppCompatResources.getDrawable(context, listBackground[position]))
            .into(holder.ivshapeable)
    }
}