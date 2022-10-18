package com.example.notes

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomePagerFragmentAdapter(
    private val arrayTitle : Array<String>,
    private val fragmentActivity: FragmentActivity
    ) : FragmentStateAdapter(fragmentActivity){
    override fun getItemCount(): Int {
        return arrayTitle.size
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return ListNoteFragment()
            1 -> return ListWorkFragment()
        }
        return ListNoteFragment()
    }
}