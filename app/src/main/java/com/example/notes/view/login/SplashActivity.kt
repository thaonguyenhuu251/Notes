package com.example.notes.view.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.notes.R
import com.example.notes.util.Constants
import com.example.notes.util.PreferencesSettings
import com.example.notes.view.home.AddNoteActivity
import com.example.notes.view.home.MainActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var appPreferences : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        appPreferences = getSharedPreferences(
            Constants.SHARED_PREFERENCES_APP,
            Context.MODE_PRIVATE
        )

        if (PreferencesSettings.getCode(this@SplashActivity)?.isEmpty() == true) {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }, 500)

        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this@SplashActivity, LoginPassword::class.java))
                finish()
            }, 1000)

        }
    }
}