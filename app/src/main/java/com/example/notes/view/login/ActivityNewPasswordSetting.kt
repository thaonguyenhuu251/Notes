package com.example.notes.view.login

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.notes.databinding.ActivityLoginPasswordBinding
import com.example.notes.databinding.ActivityNewPasswordBinding
import com.example.notes.databinding.ActivityTrashBinding
import com.example.notes.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ActivityNewPasswordSetting : AppCompatActivity() {
    private lateinit var binding: ActivityNewPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityNewPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.viewpagerPassword.adapter = ViewPagerFragmentAdapter(this@ActivityNewPasswordSetting)
        binding.viewpagerPassword.isUserInputEnabled = true
        setTabLayout()
    }

    private fun setTabLayout(){
        TabLayoutMediator(binding.tablayoutPassword, binding.viewpagerPassword) { tab, position ->

        }.attach()

        binding.tablayoutPassword.addOnTabSelectedListener(object  : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    private class ViewPagerFragmentAdapter(
        fragmentActivity: FragmentActivity
    ) :
        FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> return FragmentNewPasswordSetting()
                1 -> return FragmentConfirmNewPasswordSetting()
            }
            return FragmentNewPasswordSetting()
        }
    }

}