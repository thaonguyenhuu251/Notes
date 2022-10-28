package com.example.notes.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.notes.R
import com.example.notes.model.Work
import com.example.notes.util.FileUtils
import com.example.notes.viewmodels.WorkDoViewModel

class WorkDoAdapter : RecyclerView.Adapter<WorkDoViewModel> {
    lateinit var context: Context
    var listWork: List<Work>
    var txtEdit: TextView? = null
    var txtExit: TextView? = null
    var txtNameWork: TextView? = null
    var txtContentWork: TextView? = null
    var txtStartDay: TextView? = null
    var txtTimeComplete: TextView? = null
    var nameWork: String? = null
    var contentWork: String? = null
    var startDay: String? = null
    var timeComplete = 0f
    var idWork: Long = 0

    constructor(context: Context, listWork: List<Work>) {
        this.context = context
        this.listWork = listWork
    }

    constructor(listWork: List<Work>) {
        this.listWork = listWork
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkDoViewModel {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.item_work, parent, false)
        return WorkDoViewModel(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: WorkDoViewModel,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.txtName.text = "Name work: " + listWork[position].nameWork
        holder.txtStartDay.text = "Start day: " + listWork[position].startDay
        holder.txtTimeComplete.text = "Time complete: " + FileUtils.formatTime(listWork[position].timeComplete)
        holder.itemView.setOnClickListener {
            nameWork = listWork[position].nameWork
            contentWork = listWork[position].contentWork
            startDay = listWork[position].startDay
            timeComplete = listWork[position].timeComplete
            idWork = listWork[position].idWork!!
        }
        if (FileUtils.isPrimeNumber(position) && position % 2 == 0) {
            Glide.with(context)
                .load("") // image url
                .error(
                    AppCompatResources.getDrawable(
                        context,
                        R.drawable.ic_celendar_color
                    )
                ) // any image in case of error
                .into(holder.imgWork)
        } else if (FileUtils.isPrimeNumber(position) && position % 2 == 1) {
            Glide.with(context)
                .load("") // image url
                .error(
                    AppCompatResources.getDrawable(
                        context,
                        R.drawable.ic_time_color
                    )
                ) // any image in case of error
                .into(holder.imgWork)
        } else {
            Glide.with(context)
                .load("") // image url
                .error(
                    AppCompatResources.getDrawable(
                        context,
                        R.drawable.ic_book_color
                    )
                ) // any image in case of error
                .into(holder.imgWork)
        }
    }

    override fun getItemCount(): Int {
        return listWork.size
    }

    fun filterList(filterlist: ArrayList<Work>) {
        // below line is to add our filtered
        // list in our course array list.
        listWork = filterlist
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged()
    }
}