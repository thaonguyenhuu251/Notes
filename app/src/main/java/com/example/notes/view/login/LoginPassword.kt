package com.example.notes.view.login
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.beautycoder.pflockscreen.PFFLockScreenConfiguration
import com.beautycoder.pflockscreen.fragments.PFLockScreenFragment
import com.beautycoder.pflockscreen.fragments.PFLockScreenFragment.OnPFLockScreenLoginListener
import com.example.notes.R
import com.example.notes.databinding.ActivityNewPasswordBinding
import com.example.notes.util.PreferencesSettings
import com.example.notes.view.home.MainActivity

class LoginPassword : AppCompatActivity() {
    private lateinit var binding: ActivityNewPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setTheme(PreferencesSettings.getBackground(this))
        binding = ActivityNewPasswordBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        showLockScreenFragment(true)
    }

    private val mLoginListener: OnPFLockScreenLoginListener = object : OnPFLockScreenLoginListener {
        override fun onCodeInputSuccessful() {
            val intent = Intent(this@LoginPassword, MainActivity::class.java)
            startActivity(intent)
        }

        override fun onFingerprintSuccessful() {
            val intent = Intent(this@LoginPassword, MainActivity::class.java)
            startActivity(intent)
        }

        override fun onPinLoginFailed() {
            Toast.makeText(this@LoginPassword, getString(R.string.pin_fail), Toast.LENGTH_SHORT).show()
        }

        override fun onFingerprintLoginFailed() {
            Toast.makeText(this@LoginPassword, getString(R.string.pin_fail), Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLockScreenFragment(isPinExist: Boolean) {
        val builder = PFFLockScreenConfiguration.Builder(this)
            .setTitle(if (isPinExist) getString(R.string.input_code) else getString(R.string.create_password))
            .setCodeLength(6)
            .setLeftButton(getString(R.string.cant_password))
            .setUseFingerprint(PreferencesSettings.getFinger(this))
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