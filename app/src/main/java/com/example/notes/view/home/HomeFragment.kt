package com.example.notes.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.notes.databinding.FragmentHomeBinding
import com.example.notes.view.components.BottomSheetSort
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {
    private val titles = arrayOf("Note", "Work")
    private var pagerAdapter: HomePagerFragmentAdapter? = null
    private lateinit var binding: FragmentHomeBinding
    var searchText: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            searchText = arguments?.getString("search");
        }
    }

    private fun initView() {
        binding.imgSort.setOnClickListener {
            val bottomSheetSort = BottomSheetSort()
            bottomSheetSort.show(childFragmentManager, BottomSheetSort.TAG)
        }
        pagerAdapter = HomePagerFragmentAdapter(titles, requireActivity())
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.isUserInputEnabled = false
        setTabLayout()
    }

    private fun setTabLayout() {
        TabLayoutMediator(binding.tbLayout, binding.viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()

        binding.tbLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        pagerAdapter?.setData1(searchText.toString())
                    }
                    1 -> {
                        pagerAdapter?.setData2(searchText.toString())
                    }
                }

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
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
    }

    private class HomePagerFragmentAdapter(
        private val arrayTitle : Array<String>,
        private val fragmentActivity: FragmentActivity
    ) : FragmentStateAdapter(fragmentActivity){

        private var fragment1Container: String? = null
        private var fragment2Container: String? = null

        fun setData1(container: String) {
            fragment1Container = container
        }

        fun setData2(container: String) {
            fragment2Container = container
        }

        override fun getItemCount(): Int {
            return arrayTitle.size
        }

        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> {
                    val fragment1 = ListNoteFragment()
                    val bundle = Bundle()
                    bundle.putString("searchItem", fragment1Container)
                    bundle.putString("fragment1Container", "")
                    fragment1.arguments = bundle
                    return fragment1
                }
                1 -> {
                    val fragment2 = ListWorkFragment()
                    val bundle = Bundle()
                    bundle.putString("searchItem", fragment2Container)
                    bundle.putString("fragment1Container", "")
                    fragment2.arguments = bundle
                    return fragment2
                }
            }
            return ListNoteFragment()
        }
    }

}