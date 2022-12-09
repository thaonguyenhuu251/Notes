package com.example.notes.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.notes.adapter.BookMarkPagerFragmentAdapter
import com.example.notes.databinding.FragmentBookMarkBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class BookMarkFragment : Fragment() {
    private val titles = arrayOf("Note", "Work")
    private lateinit var binding:FragmentBookMarkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookMarkBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
    }

    private fun initView() {
        binding.viewPager.adapter = BookMarkPagerFragmentAdapter(titles, requireActivity())
        binding.viewPager.isUserInputEnabled = false

        setTabLayout()
    }

    private fun setTabLayout() {
        TabLayoutMediator(binding.tablayout, binding.viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()

        binding.tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {


            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

}