package com.example.notes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.example.notes.R

class LoginPasswordExpandableAdapter internal constructor(
    private val context: Context,
    private val eGroupList: MutableList<String>,
    private val listChild: MutableList<MutableList<String>>
) : BaseExpandableListAdapter() {
    override fun getGroupCount(): Int {
        return eGroupList.size
    }

    override fun getChildrenCount(p0: Int): Int {
        return listChild[p0].size
    }

    override fun getGroup(p0: Int): Any {
        return eGroupList[p0]
    }

    override fun getChild(p0: Int, p1: Int): Any {
        return listChild[p0][p1]
    }

    override fun getGroupId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getChildId(p0: Int, p1: Int): Long {
        return p1.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(p0: Int, p1: Boolean, p2: View?, p3: ViewGroup?): View? {
        var convertView = p2

        if(convertView ==  null){
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.expandable_list_item_group, null)
        }

        val txtGroupTitle = convertView?.findViewById<TextView>(R.id.txtGroupTitle)
        txtGroupTitle?.text = getGroup(p0).toString()
        return convertView
    }

    override fun getChildView(p0: Int, p1: Int, p2: Boolean, p3: View?, p4: ViewGroup?): View? {
        var convertView = p3

        if(convertView ==  null){
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.expandable_list_item_child, null)
        }

        val txtChildTitle = convertView?.findViewById<TextView>(R.id.txtChildTitle)
        txtChildTitle?.text = getChild(p0, p1).toString()
        return convertView
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
    }

}