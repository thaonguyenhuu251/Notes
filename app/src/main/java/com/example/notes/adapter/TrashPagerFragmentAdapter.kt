package com.example.notes.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.notes.view.home.ListNoteFragment
import com.example.notes.view.home.ListWorkFragment
import com.example.notes.view.home.NoteTrashFragment
import com.example.notes.view.home.WorkTrashFragment

class TrashPagerFragmentAdapter(
    private val arrayTitle : Array<String>,
    private val fragmentActivity: FragmentActivity
    ) : FragmentStateAdapter(fragmentActivity){
    override fun getItemCount(): Int {
        return arrayTitle.size
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return NoteTrashFragment()
            1 -> return WorkTrashFragment()
        }
        return NoteTrashFragment()
    }
}