package com.example.notes

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import com.example.notes.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {
    var homeFragment = HomeFragment()
    var chartFragment = ChartFragment()
    var settingFragment = SettingFragment()
    var notificationFragment = NotificationFragment()

    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        binding.root.setOnClickListener { FileUtils.hideKeyboard(this) }
        binding.content.setOnClickListener { FileUtils.hideKeyboard(this) }
        binding.bottomMenu.fabAdd.setOnClickListener {
            val i = Intent(this, AddNoteActivity::class.java)
            startActivity(i)
        }
        setBottomMenu()
        setDrawer()
        eventSearch()
    }

    private fun setBottomMenu() {
        supportFragmentManager.beginTransaction().replace(R.id.content, homeFragment).commit()
        binding.bottomMenu.bottomNavigationView.background = null
        binding.bottomMenu.bottomNavigationView.menu.getItem(0).isEnabled = true
        binding.bottomMenu.bottomNavigationView.setOnItemSelectedListener(label@ NavigationBarView.OnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.home -> {
                    binding.rltSearch.visibility = View.VISIBLE
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.content, homeFragment).commit()
                    return@OnItemSelectedListener true
                }
                R.id.chart -> {
                    binding.rltSearch.visibility = View.GONE
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.content, chartFragment).commit()
                    return@OnItemSelectedListener true
                }

                R.id.setting -> {
                    binding.rltSearch.visibility = View.GONE
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.content, settingFragment).commit()
                    return@OnItemSelectedListener true
                }
                R.id.notify -> {
                    binding.rltSearch.visibility = View.GONE
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.content, notificationFragment).commit()
                    return@OnItemSelectedListener true
                }
            }
            false
        })
    }

    private fun setDrawer() {
        mDrawerLayout = binding.drawerMain
        openDrawer()
    }

    private fun openDrawer() {
        val navigationView = binding.navMain
        navigationView.setNavigationItemSelectedListener { true }
        binding.imgMenu.setOnClickListener { view -> mDrawerLayout.openDrawer(GravityCompat.START) }
        mDrawerLayout.addDrawerListener(
            object : DrawerListener {
                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                    // Respond when the drawer's position changes
                }

                override fun onDrawerOpened(drawerView: View) {
                    // Respond when the drawer is opened
                }

                override fun onDrawerClosed(drawerView: View) {
                    // Respond when the drawer is closed
                }

                override fun onDrawerStateChanged(newState: Int) {
                    // Respond when the drawer motion state changes
                }
            }
        )
    }

    private fun eventSearch() {
        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.imgClose.setOnClickListener {
            binding.edtSearch.setText("")
        }
    }

}