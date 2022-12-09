package com.example.notes.view.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.beautycoder.pflockscreen.PFFLockScreenConfiguration
import com.beautycoder.pflockscreen.fragments.PFLockScreenFragment
import com.beautycoder.pflockscreen.viewmodels.PFPinCodeViewModel
import com.example.notes.R
import com.example.notes.databinding.ActivityNewPasswordBinding
import com.example.notes.util.PreferencesSettings
import com.example.notes.view.home.MainActivity

class NewPasswordSettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setTheme(PreferencesSettings.getBackground(this))
        binding = ActivityNewPasswordBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        showLockScreenFragment(false)
    }

    private val mCodeCreateListener: PFLockScreenFragment.OnPFLockScreenCodeCreateListener =
        object : PFLockScreenFragment.OnPFLockScreenCodeCreateListener {
            override fun onCodeCreated(encodedCode: String) {
                PreferencesSettings.saveToPref(this@NewPasswordSettingActivity, encodedCode)
                val intent = Intent(this@NewPasswordSettingActivity, MainActivity::class.java)
                startActivity(intent)
            }

            override fun onNewCodeValidationFailed() {
                Toast.makeText(this@NewPasswordSettingActivity, getString(R.string.code_error), Toast.LENGTH_SHORT)
                    .show()
            }
        }

    private fun showLockScreenFragment(isPinExist: Boolean) {
        val builder = PFFLockScreenConfiguration.Builder(this)
            .setTitle(if (isPinExist) getString(R.string.input_code) else getString(R.string.create_password))
            .setCodeLength(6)
            .setLeftButton(getString(R.string.cant_password))
            .setNewCodeValidation(true)
            .setNewCodeValidationTitle(getString(R.string.input_pass_again))
            .setUseFingerprint(false)
        val fragment = PFLockScreenFragment()
        fragment.setOnLeftButtonClickListener {

        }
        builder.setMode(if (isPinExist) PFFLockScreenConfiguration.MODE_AUTH else PFFLockScreenConfiguration.MODE_CREATE)
        if (isPinExist) {
            val intent = Intent(this@NewPasswordSettingActivity, MainActivity::class.java)
            startActivity(intent)
        }
        fragment.setConfiguration(builder.build())
        fragment.setCodeCreateListener(mCodeCreateListener)
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerViewSetting, fragment).commit()
    }

}