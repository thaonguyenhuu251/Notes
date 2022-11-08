package com.example.notes.view.home

import android.os.Bundle
import android.view.View
import com.example.notes.App
import com.example.notes.adapter.DisplayPagerFragmentAdapter
import com.example.notes.base.BaseActivity
import com.example.notes.databinding.ActicityDisplaySettingsBinding
import com.example.notes.util.Event
import com.example.notes.util.PreferencesSettings
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import io.reactivex.rxjava3.disposables.Disposable

class DisplaySettingsActivity : BaseActivity(){
    private val titles = arrayOf("Background", "Color")
    private lateinit var binding: ActicityDisplaySettingsBinding

    private var disposable: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setTheme(PreferencesSettings.getBackground(this))
        binding = ActicityDisplaySettingsBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        initView()

        disposable = App.eventBus.subscribe{
            it[Event.EVENT_CHANGE_BACKGROUND]?.let { data->
                (data as Int?)?.let { background ->
                    super.setTheme(background)
                }

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //disposable?.dispose()
    }

    private fun initView() {
        binding.viewPager.adapter = DisplayPagerFragmentAdapter(titles, this)
        binding.viewPager.isUserInputEnabled = true
        setTabLayout()
        binding.txtBack.setOnClickListener {
            onBackPressed()
        }
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

}