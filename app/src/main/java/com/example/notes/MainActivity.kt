package com.example.notes

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.notes.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {
    var homeFragment = HomeFragment()
    var chartFragment = ChartFragment()
    var settingFragment = SettingFragment()
    var notificationFragment = NotificationFragment()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        setBottomMenu()
    }

    private fun setBottomMenu () {
        binding.bottomMenu.bottomNavigationView.background = null
        binding.bottomMenu.bottomNavigationView.menu.getItem(0).isEnabled = true
        binding.bottomMenu.bottomNavigationView.setOnItemSelectedListener(label@ NavigationBarView.OnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.home -> {
                    binding.txtTitle.text = "Home"
                    binding.txtTime.visibility = View.VISIBLE
                    binding.imgSort.visibility = View.VISIBLE
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.content, homeFragment).commit()
                    return@OnItemSelectedListener true
                }
                R.id.chart -> {
                    binding.txtTime.visibility = View.GONE
                    binding.imgSort.visibility = View.GONE
                    binding.txtTitle.text = "Chart"
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.content, chartFragment).commit()
                    return@OnItemSelectedListener true
                }

                R.id.setting -> {
                    binding.txtTitle.text = "Setting"
                    binding.txtTime.visibility = View.GONE
                    binding.imgSort.visibility = View.GONE
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.content, settingFragment).commit()
                    return@OnItemSelectedListener true
                }
                R.id.notify -> {
                    binding.txtTime.visibility = View.GONE
                    binding.imgSort.visibility = View.GONE
                    binding.txtTitle.text = "Notification"
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.content, notificationFragment).commit()
                    return@OnItemSelectedListener true
                }
            }
            false
        })
    }
}