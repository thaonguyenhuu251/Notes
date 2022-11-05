package com.example.notes.view.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.beautycoder.pflockscreen.PFFLockScreenConfiguration
import com.beautycoder.pflockscreen.fragments.PFLockScreenFragment
import com.example.notes.R
import com.example.notes.databinding.ActivityNewPasswordBinding
import com.example.notes.util.PreferencesSettings
import com.example.notes.view.home.MainActivity

class ConfirmDeletePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setTheme(PreferencesSettings.getBackground(this))
        binding = ActivityNewPasswordBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        showLockScreenFragment(true)
    }

    private val mLoginListener: PFLockScreenFragment.OnPFLockScreenLoginListener = object :
        PFLockScreenFragment.OnPFLockScreenLoginListener {
        @SuppressLint("ApplySharedPref")
        override fun onCodeInputSuccessful() {
            PreferencesSettings.saveToFinger(this@ConfirmDeletePasswordActivity, false)
            PreferencesSettings.saveToPref(this@ConfirmDeletePasswordActivity, null)
            val intent = Intent(this@ConfirmDeletePasswordActivity, MainActivity::class.java)
            startActivity(intent)
        }

        override fun onFingerprintSuccessful() {

        }

        override fun onPinLoginFailed() {
            Toast.makeText(this@ConfirmDeletePasswordActivity, "Pin failed", Toast.LENGTH_SHORT).show()
        }

        override fun onFingerprintLoginFailed() {

        }
    }

    private fun showLockScreenFragment(isPinExist: Boolean) {
        val builder = PFFLockScreenConfiguration.Builder(this)
            .setTitle(if (isPinExist) "Confirm Password" else "Create Code")
            .setCodeLength(6)
            .setUseFingerprint(false)
        val fragment = PFLockScreenFragment()
        fragment.setOnLeftButtonClickListener {

        }

        builder.setMode(if (isPinExist) PFFLockScreenConfiguration.MODE_AUTH else PFFLockScreenConfiguration.MODE_CREATE)
        if (isPinExist) {
            fragment.setEncodedPinCode(PreferencesSettings.getCode(this))
            fragment.setLoginListener(mLoginListener)
        }
        fragment.setConfiguration(builder.build())
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerViewSetting, fragment).commit()
    }

}