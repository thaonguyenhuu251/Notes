package com.example.notes.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.model.Alarm
import com.example.notes.model.Work

class NotifAdapter : ListAdapter<Alarm, NotifAdapter.NotifHolder>(DiffCallback()) {
    lateinit var context: Context

    class NotifHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_notif, parent, false)
        return NotifHolder(v)
    }

    override fun onBindViewHolder(holder: NotifHolder, position: Int) {
        val currentItem = getItem(position)
        val txtNotifTitle = holder.itemView.findViewById<TextView>(R.id.txtNotifTitle)
        val txtNotifContent = holder.itemView.findViewById<TextView>(R.id.txtNotifContent)

        txtNotifTitle.setText(currentItem.title)
        txtNotifContent.setText(currentItem.content)
    }


    class DiffCallback : DiffUtil.ItemCallback<Alarm>() {
        override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm) =
            oldItem.alarmId == newItem.alarmId

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm) =
            oldItem == newItem
    }
}

