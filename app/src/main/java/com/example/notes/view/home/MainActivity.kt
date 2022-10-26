package com.example.notes.view.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import com.example.notes.*
import com.example.notes.databinding.ActivityMainBinding
import com.example.notes.util.Constants
import com.example.notes.util.Constants.SHARED_PREFERENCES_KEY_COLOR
import com.example.notes.util.FileUtils
import com.example.notes.view.login.LoginPassword
import com.google.android.material.navigation.NavigationBarView


class MainActivity : AppCompatActivity() {
    var homeFragment = HomeFragment()
    var bookMarkFragment = BookMarkFragment()
    var settingFragment = SettingFragment()
    var notificationFragment = NotificationFragment()

    private lateinit var appPreferences : SharedPreferences
    var appTheme = 0
    var themeColor = 0
    var appColor = 0

    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        appPreferences = getSharedPreferences(SHARED_PREFERENCES_KEY_COLOR, Context.MODE_PRIVATE)
        appColor = appPreferences.getInt("color", 0)
        appTheme = appPreferences.getInt("theme", 0)
        themeColor = appColor
        Constants.color = appColor

        if (themeColor == 0){
            setTheme(Constants.theme);
        }else if (appTheme == 0){
            setTheme(Constants.theme);
        }else{
            setTheme(appTheme);
        }
        setContentView(view)

        binding.root.setOnClickListener {
            FileUtils.hideKeyboard(this)
        }
        binding.content.setOnClickListener {
            FileUtils.hideKeyboard(this)
        }

        binding.bottomMenu.circleMenu.setOnItemClickListener {
            when (it) {
                0-> {
                    Handler(Looper.getMainLooper()).postDelayed({
                        val i = Intent(this, AddNoteActivity::class.java)
                        startActivity(i)
                    }, 500)
                }
                1-> {
                    Handler(Looper.getMainLooper()).postDelayed({
                        val i = Intent(this, AddNoteActivity::class.java)
                        startActivity(i)
                    }, 500)
                }
                2-> {
                    Handler(Looper.getMainLooper()).postDelayed({
                        val i = Intent(this, AddWorkActivity::class.java)
                        startActivity(i)
                    }, 500)
                }
            }

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
                        .replace(R.id.content, bookMarkFragment).commit()
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
        binding.imgMenu.setOnClickListener {
            binding.bottomMenu.circleMenu.hideCircleMenu()
            mDrawerLayout.openDrawer(GravityCompat.START)
            FileUtils.hideKeyboard(this)
        }

        mDrawerLayout.addDrawerListener(
            object : DrawerListener {
                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                    // Respond when the drawer's position changes
                }

                override fun onDrawerOpened(drawerView: View) {
                    // Respond when the drawer is opened
                    drawerView.findViewById<TextView>(R.id.text_ContactUs).setOnClickListener {
                        askPermissionAndCall()
                    }

                    drawerView.findViewById<TextView>(R.id.txtClockApp).setOnClickListener {
                        val i = Intent(this@MainActivity, LoginPassword::class.java)
                        startActivity(i)
                    }
                }

                override fun onDrawerClosed(drawerView: View) {
                    // Respond when the drawer is closed
                    binding.bottomMenu.circleMenu.showCircleMenu()
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

    private fun askPermissionAndCall() {

        // With Android Level >= 23, you have to ask the user
        // for permission to Call.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // 23

            // Check if we have Call permission
            val sendSmsPermisson = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            )
            if (sendSmsPermisson != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                requestPermissions(
                    arrayOf(Manifest.permission.CALL_PHONE),
                    Constants.MY_PERMISSION_REQUEST_CODE_CALL_PHONE
                )
                return
            }
        }
        callNow()
    }

    @SuppressLint("MissingPermission")
    private fun callNow() {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:0393397641")
        try {
            this.startActivity(callIntent)
        } catch (ex: Exception) {
            Toast.makeText(
                applicationContext, "Your call failed... " + ex.message,
                Toast.LENGTH_LONG
            ).show()
            ex.printStackTrace()
        }
    }

    // When you have the request results
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions!!, grantResults)
        when (requestCode) {
            Constants.MY_PERMISSION_REQUEST_CODE_CALL_PHONE -> {


                // Note: If request is cancelled, the result arrays are empty.
                // Permissions granted (CALL_PHONE).
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    Toast.makeText(this, "Permission granted!", Toast.LENGTH_LONG).show()
                    callNow()
                } else {
                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    // When results returned
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.MY_PERMISSION_REQUEST_CODE_CALL_PHONE) {
            when (resultCode) {
                RESULT_OK -> {
                    // Do something with data (Result returned).
                    Toast.makeText(this, "Action OK", Toast.LENGTH_LONG).show()
                }
                RESULT_CANCELED -> {
                    Toast.makeText(this, "Action Cancelled", Toast.LENGTH_LONG).show()
                }
                else -> {
                    Toast.makeText(this, "Action Failed", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}