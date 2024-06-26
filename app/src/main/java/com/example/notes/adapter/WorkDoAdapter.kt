package com.example.notes.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.notes.R
import com.example.notes.model.Work
import com.example.notes.util.FileUtils
import com.example.notes.util.SimpleDateFormat
import com.example.notes.view.components.ShowResultDialog

class WorkDoAdapter : ListAdapter<Work, WorkDoAdapter.WorkHolder>(DiffCallback()) {
    lateinit var context: Context

    class WorkHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_work, parent, false)
        context = parent.context
        return WorkHolder(v)
    }

    override fun onBindViewHolder(holder: WorkHolder, position: Int) {
        val currentItem = getItem(position)
        val txtName = holder.itemView.findViewById<TextView>(R.id.txtName)
        val txtContentWork = holder.itemView.findViewById<TextView>(R.id.txtContentWork)
        val txtTimeComplete = holder.itemView.findViewById<TextView>(R.id.txtTimeComplete)
        val imgWork = holder.itemView.findViewById<ImageView>(R.id.imgWork)

        txtName.text = context.getString(R.string.work_name, currentItem.nameWork)
        txtContentWork.text = context.getString(R.string.work_content, currentItem.contentWork)
        txtTimeComplete.text = SimpleDateFormat(context.getString(R.string.work_day)).format(currentItem.timeNotify) + " " +
                SimpleDateFormat(context.getString(R.string.work_time)).format(currentItem.timeNotify)

        holder.itemView.setOnClickListener {

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
                .into(imgWork)
        } else if (FileUtils.isPrimeNumber(position) && position % 2 == 1) {
            Glide.with(context)
                .load("") // image url
                .error(
                    AppCompatResources.getDrawable(
                        context,
                        R.drawable.ic_time_color
                    )
                ) // any image in case of error
                .into(imgWork)
        } else {
            Glide.with(context)
                .load("") // image url
                .error(
                    AppCompatResources.getDrawable(
                        context,
                        R.drawable.ic_book_color
                    )
                ) // any image in case of error
                .into(imgWork)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Work>() {
        override fun areItemsTheSame(oldItem: Work, newItem: Work) =
            oldItem.idWork == newItem.idWork

        override fun areContentsTheSame(oldItem: Work, newItem: Work) =
            oldItem == newItem
    }

}
