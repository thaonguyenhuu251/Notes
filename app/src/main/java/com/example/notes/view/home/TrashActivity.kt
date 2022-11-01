package com.example.notes.view.home

import android.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.notes.adapter.TrashPagerFragmentAdapter
import com.example.notes.databinding.ActivityTrashBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class TrashActivity : AppCompatActivity() {
    private val titles = arrayOf("Note", "Work")
    lateinit var binding: ActivityTrashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

    }

    private fun initView() {
        binding.viewPager.adapter = TrashPagerFragmentAdapter(titles,this)
        binding.viewPager.isUserInputEnabled = true
        setTabLayout()
    }


    private fun setTabLayout() {
        TabLayoutMediator(binding.tablayoutTrash, binding.viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()

        binding.tablayoutTrash.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {


            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }
}