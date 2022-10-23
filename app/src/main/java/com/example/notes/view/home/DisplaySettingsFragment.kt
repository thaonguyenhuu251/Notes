package com.example.notes.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.notes.adapter.DisplayPagerFragmentAdapter
import com.example.notes.databinding.LayoutDisplaySettingsBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DisplaySettingsFragment : Fragment(){
    private val titles = arrayOf("Background", "Color")
    private lateinit var binding:LayoutDisplaySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun initView() {
        binding.viewPager.adapter = DisplayPagerFragmentAdapter(titles, requireActivity())
        binding.viewPager.isUserInputEnabled = true
        setTabLayout()
    }

    private fun setTabLayout() {
        TabLayoutMediator(binding.tbLayout, binding.viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()

        binding.tbLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {


            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutDisplaySettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DisplaySettingsFragment().apply {

            }
    }
}