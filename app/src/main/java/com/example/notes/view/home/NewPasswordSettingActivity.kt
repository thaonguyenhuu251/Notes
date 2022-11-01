package com.example.notes.view.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.notes.databinding.ActivityNewPasswordBinding
import com.example.notes.view.login.ConfirmNewPasswordSettingFragment
import com.example.notes.view.login.NewPasswordSettingFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class NewPasswordSettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewPasswordBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        initView()
    }
    private fun initView() {
        binding.viewpagerPassword.adapter = ViewPagerFragmentAdapter(this@NewPasswordSettingActivity)
        binding.viewpagerPassword.isUserInputEnabled = false
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
                0 -> return NewPasswordSettingFragment()
                1 -> return ConfirmNewPasswordSettingFragment()
            }
            return NewPasswordSettingFragment()
        }
    }

}