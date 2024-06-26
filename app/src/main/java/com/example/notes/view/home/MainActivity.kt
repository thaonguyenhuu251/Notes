package com.example.notes.view.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.core.widget.doOnTextChanged
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.example.notes.App
import com.example.notes.R
import com.example.notes.base.BaseActivity
import com.example.notes.database.NoteRoomDatabaseClass
import com.example.notes.database.UserDatabase
import com.example.notes.database.WorkRoomDatabaseClass
import com.example.notes.databinding.ActivityMainBinding
import com.example.notes.model.Alarm
import com.example.notes.model.Note
import com.example.notes.model.Work
import com.example.notes.util.Constants
import com.example.notes.util.Constants.FACEBOOK_PAGE_ID
import com.example.notes.util.Constants.FACEBOOK_URL
import com.example.notes.util.Event
import com.example.notes.util.FileUtils
import com.example.notes.util.PreferencesSettings
import com.example.notes.view.login.LoginPassword
import com.example.notes.viewmodels.CreateAlarmViewModel
import com.google.android.material.navigation.NavigationBarView
import de.hdodenhof.circleimageview.CircleImageView
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : BaseActivity() {
    var homeFragment = HomeFragment()
    var bookMarkFragment = BookMarkFragment()
    var settingFragment = SettingFragment()
    var notificationFragment = NotificationFragment()

    private var disposable: Disposable? = null

    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var binding: ActivityMainBinding

    private val workDatabase by lazy { WorkRoomDatabaseClass.getDataBase(this).workDao() }
    private val noteDatabase by lazy { NoteRoomDatabaseClass.getDataBase(this).noteDao() }
    private val userDatabase by lazy { UserDatabase.getDataBase(applicationContext).userDao() }
    private var createAlarmViewModel: CreateAlarmViewModel? = null

    private val newWorkResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val id = result.data?.getLongExtra(Constants.WORK_ID, System.currentTimeMillis())
                val nameWork = result.data?.getStringExtra(Constants.WORK_NAME)
                val timeNotify =
                    result.data?.getLongExtra(Constants.WORK_TIME, System.currentTimeMillis())
                val contentWork = result.data?.getStringExtra(Constants.WORK_CONTENT)
                val isNoty = result.data?.getBooleanExtra(Constants.WORK_NOTIFY, false)
                val alarm = Alarm(
                    Random().nextInt(Int.MAX_VALUE),
                    timeNotify,
                    nameWork,
                    contentWork,
                    System.currentTimeMillis(),
                    started = true,
                    recurring = false,
                    isVibration = false
                )

                if (isNoty == true && timeNotify != null) {
                    createAlarmViewModel?.insert(alarm)
                    alarm.schedule(this)
                } else {
                    alarm.cancelAlarm(this)
                }

                val newNote = Work(id, nameWork, contentWork, timeNotify, isNoty, false)
                lifecycleScope.launch {
                    workDatabase.addWork(newNote)
                }

            }
        }

    private val newNoteResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val id = result.data?.getLongExtra(Constants.NOTE_ID, System.currentTimeMillis())
                val titleWork = result.data?.getStringExtra(Constants.NOTE_TITLE)
                val timeNotify =
                    result.data?.getLongExtra(Constants.NOTE_TIME, System.currentTimeMillis())
                val contentNote = result.data?.getStringExtra(Constants.NOTE_CONTENT)
                val editNote = Note(id, titleWork, contentNote, timeNotify, false)
                lifecycleScope.launch (Dispatchers.IO){
                    noteDatabase.addNote(editNote)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setTheme(PreferencesSettings.getBackground(this))
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        createAlarmViewModel = ViewModelProviders.of(this)[CreateAlarmViewModel::class.java]

        binding.root.setOnClickListener {
            FileUtils.hideKeyboard(this)
        }
        binding.content.setOnClickListener {
            FileUtils.hideKeyboard(this)
        }
        binding.bottomMenu.circleMenu.setOnItemClickListener {
            when (it) {
                0 -> {
                    Handler(Looper.getMainLooper()).postDelayed({
                        val i = Intent(this, AddNoteActivity::class.java)
                        newNoteResultLauncher.launch(i)
                        //overridePendingTransition(R.animator.slide_out_left, R.animator.slide_in_right)
                    }, 500)
                }
                1 -> {
                    Handler(Looper.getMainLooper()).postDelayed({
                    }, 500)
                }
                2 -> {
                    Handler(Looper.getMainLooper()).postDelayed({
                        val i = Intent(this, AddWorkActivity::class.java)
                        newWorkResultLauncher.launch(i)
                    }, 500)
                }
            }

        }
        setBottomMenu()
        setDrawer()
        eventSearch()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase)
    }

    override fun onResume() {
        super.onResume()
        disposable = App.eventBus.subscribe {
            it[Event.EVENT_CHANGE_BACKGROUND]?.let {
                recreate()
            }

            it[Event.EVENT_CHANGE_LANGUAGE]?.let {
                recreate()

            }
        }
        setDrawer()
    }


    private fun setBottomMenu() {
        when (PreferencesSettings.getLayout(this)) {
            0 -> {
                supportFragmentManager.beginTransaction().replace(R.id.content, homeFragment)
                    .commit()
                binding.rltSearch.visibility = View.VISIBLE
            }
            1 -> {
                binding.rltSearch.visibility = View.GONE
                supportFragmentManager.beginTransaction().replace(R.id.content, bookMarkFragment)
                    .commit()
            }
            2 -> {
                binding.rltSearch.visibility = View.GONE
                supportFragmentManager.beginTransaction()
                    .replace(R.id.content, notificationFragment).commit()
            }
            else -> {
                binding.rltSearch.visibility = View.GONE
                supportFragmentManager.beginTransaction().replace(R.id.content, settingFragment)
                    .commit()
            }
        }
        binding.bottomMenu.bottomNavigationView.background = null
        binding.bottomMenu.bottomNavigationView.menu.getItem(0).isEnabled = true
        binding.bottomMenu.bottomNavigationView.setOnItemSelectedListener(label@ NavigationBarView.OnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.home -> {
                    binding.rltSearch.visibility = View.VISIBLE
                    PreferencesSettings.setLayout(this, 0)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.content, homeFragment).commit()
                    return@OnItemSelectedListener true
                }
                R.id.chart -> {
                    binding.rltSearch.visibility = View.GONE
                    PreferencesSettings.setLayout(this, 1)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.content, bookMarkFragment).commit()
                    return@OnItemSelectedListener true
                }

                R.id.setting -> {
                    binding.rltSearch.visibility = View.GONE
                    PreferencesSettings.setLayout(this, 3)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.content, settingFragment).commit()
                    return@OnItemSelectedListener true
                }
                R.id.notify -> {
                    PreferencesSettings.setLayout(this, 2)
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
        if(userDatabase.getUser().size > 0){
            var username:String = userDatabase.getUser().get(0).userName.toString()
            if(username.isNullOrEmpty()){
                username = R.string.login.toString()
            }
            mDrawerLayout.findViewById<TextView>(R.id.txtMenuUserName).setText(username)
        }



        if (PreferencesSettings.getCode(this)?.isEmpty() == false) {
            mDrawerLayout.findViewById<TextView>(R.id.txtClockApp).visibility = View.VISIBLE
        } else {
            mDrawerLayout.findViewById<TextView>(R.id.txtClockApp).visibility = View.GONE
        }

        mDrawerLayout.findViewById<CircleImageView>(R.id.ivProfileUser).setOnClickListener{
            val i = Intent(this, ProfileActivity::class.java)
            startActivity(i)
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
                    drawerView.findViewById<TextView>(R.id.txtAboutMe).setOnClickListener {
                        //startActivity(getOpenFacebookIntent())
                        openFacebookProfile(this@MainActivity)
                    }

                    drawerView.findViewById<TextView>(R.id.txtChatWithMe).setOnClickListener {
                        startActivity(
                            newFacebookIntent(
                                this@MainActivity.packageManager,
                                "https://www.facebook.com/PhanAnhHaUI"
                            )
                        )
                    }

                    drawerView.findViewById<TextView>(R.id.txtMyProfile).setOnClickListener {
                        startActivity(Intent(this@MainActivity, ProfileActivity::class.java))
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
        binding.edtSearch.doOnTextChanged { text, start, before, count ->
            if (homeFragment.getFragment() == 0) {
                Event.searchDocument(binding.edtSearch.text.toString())
            } else {
                Event.searchDocument(binding.edtSearch.text.toString())
            }
        }

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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
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
                    Toast.makeText(this, getString(R.string.action_ok), Toast.LENGTH_LONG).show()
                }
                RESULT_CANCELED -> {
                    Toast.makeText(this, getString(R.string.action_cancel), Toast.LENGTH_LONG).show()
                }
                else -> {
                    Toast.makeText(this, getString(R.string.action_fail), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun getFacebookPageURL(context: Context): String? {
        val packageManager: PackageManager = context.packageManager
        return try {
            val versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode
            if (versionCode >= 3002850) { //newer versions of fb app
                "fb://facewebmodal/f?href=$FACEBOOK_URL"
            } else { //older versions of fb app
                "fb://page/$FACEBOOK_PAGE_ID"
            }
        } catch (e: PackageManager.NameNotFoundException) {
            FACEBOOK_URL //normal web url
        }
    }

    fun getOpenFacebookIntent(): Intent? {
        return try {
            this.packageManager.getPackageInfo("com.facebook.katana", 0)
            Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/751931421605113"))
        } catch (e: Exception) {
            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/PhanAnhcs2501"))
        }
    }

    private fun newFacebookIntent(pm: PackageManager, url: String): Intent {
        var uri = Uri.parse(url)
        try {
            val applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0)
            if (applicationInfo.enabled) {
                // http://stackoverflow.com/a/24547437/1048340
                uri = Uri.parse("fb://facewebmodal/f?href=$url")
            }
        } catch (ignored: PackageManager.NameNotFoundException) {
        }
        return Intent(Intent.ACTION_VIEW, uri)
    }

    private fun openFacebookProfile(activity: Activity) {
        val facebookIntent = Intent(Intent.ACTION_VIEW)
        val facebookUrl: String = getFacebookPageURL(activity).toString()
        facebookIntent.data = Uri.parse(facebookUrl)
        activity.startActivity(facebookIntent)
    }

}