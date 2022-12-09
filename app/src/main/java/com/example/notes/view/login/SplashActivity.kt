package com.example.notes.view.login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.notes.R
import com.example.notes.base.BaseActivity
import com.example.notes.util.PreferencesSettings
import com.example.notes.view.home.MainActivity


@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setTheme(PreferencesSettings.getBackground(this))
        setContentView(R.layout.activity_splash)
        PreferencesSettings.setLayout(this, 0)
        if (PreferencesSettings.getCode(this@SplashActivity)?.isEmpty() == true) {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }, 500)

        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this@SplashActivity, LoginPassword::class.java))
                finish()
            }, 500)

        }
    }
}