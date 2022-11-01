package com.example.notes.view.login

import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.notes.R
import com.example.notes.databinding.ActivityLoginPasswordBinding
import com.example.notes.util.PreferencesSettings
import com.example.notes.view.home.MainActivity
import com.kevalpatel.passcodeview.PinView
import com.kevalpatel.passcodeview.authenticator.PasscodeViewPinAuthenticator
import com.kevalpatel.passcodeview.indicators.CircleIndicator
import com.kevalpatel.passcodeview.interfaces.AuthenticationListener
import com.kevalpatel.passcodeview.keys.KeyNamesBuilder
import com.kevalpatel.passcodeview.keys.RoundKey

class LoginPasswordPin : AppCompatActivity() {
    private lateinit var binding: ActivityLoginPasswordBinding
    lateinit var mPinView: PinView
    private var cancellationSignal: CancellationSignal? = null

    private val authenticationCallback: BiometricPrompt.AuthenticationCallback
        get() =
            @RequiresApi(Build.VERSION_CODES.P)
            object: BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                    super.onAuthenticationError(errorCode, errString)
                    notifyUser("Authentication error: $errString")
                }
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)
                    notifyUser("Authentication Success!")
                    startActivity(Intent(this@LoginPasswordPin, MainActivity::class.java))
                }
            }
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPasswordBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        mPinView = binding.patternView
        val correctPattern = PreferencesSettings.getCode(this@LoginPasswordPin)?.map { it.digitToInt() }?.toIntArray()
        mPinView.pinAuthenticator = PasscodeViewPinAuthenticator(correctPattern)

        //set number
        mPinView.setKey(
            RoundKey.Builder(mPinView)
                .setKeyPadding(R.dimen.key_padding)
                .setKeyStrokeColorResource(R.color.white)
                .setKeyStrokeWidth(R.dimen.key_stroke_width)
                .setKeyTextColorResource(R.color.white)
                .setKeyTextSize(R.dimen.key_text_size)
        )

        //set key
        mPinView.setIndicator(
            CircleIndicator.Builder(mPinView)
                .setIndicatorRadius(R.dimen.indicator_radius)
                .setIndicatorFilledColorResource(R.color.white)
                .setIndicatorStrokeColorResource(R.color.white)
                .setIndicatorStrokeWidth(R.dimen.indicator_stroke_width)
        )


        mPinView.pinLength = 6


        mPinView.setKeyNames(
            KeyNamesBuilder()
                .setKeyOne(this, R.string.key_1)
                .setKeyTwo(this, R.string.key_2)
                .setKeyThree(this, R.string.key_3)
                .setKeyFour(this, R.string.key_4)
                .setKeyFive(this, R.string.key_5)
                .setKeySix(this, R.string.key_6)
                .setKeySeven(this, R.string.key_7)
                .setKeyEight(this, R.string.key_8)
                .setKeyNine(this, R.string.key_9)
                .setKeyZero(this, R.string.key_0)
        )
        mPinView.title = "Enter the PIN"
        mPinView.setAuthenticationListener(object : AuthenticationListener {
            override fun onAuthenticationSuccessful() {
                startActivity(Intent(this@LoginPasswordPin,MainActivity::class.java))
                finish()
            }

            override fun onAuthenticationFailed() {
                Toast.makeText(this@LoginPasswordPin, "Incorrect password!", Toast.LENGTH_SHORT)
                    .show()
            }
        })

        binding.txtBack.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }

        if (!checkBiometricSupport()) {
            binding.imgPasswordFingerprint.visibility = View.GONE
        }


        binding.imgPasswordFingerprint.setOnClickListener{
            val biometricPrompt : BiometricPrompt = BiometricPrompt.Builder(this)
                .setTitle("Title")
                .setSubtitle("Authentication is required")
                .setDescription("Fingerprint Authentication")
                .setNegativeButton("Cancel", this.mainExecutor) {
                        dialog, which ->
                }.build()
            biometricPrompt.authenticate(getCancellationSignal(), mainExecutor, authenticationCallback)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putIntArray(ARG_CURRENT_PIN, mPinView.currentTypedPin)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        mPinView.currentTypedPin = savedInstanceState.getIntArray(ARG_CURRENT_PIN)
    }

    companion object {
        private const val ARG_CURRENT_PIN = "current_pin"
    }

    private fun notifyUser(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun getCancellationSignal(): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            notifyUser("Authentication was cancelled by the user")
        }
        return cancellationSignal as CancellationSignal
    }
    private fun checkBiometricSupport(): Boolean {
        val keyguardManager : KeyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        if(!keyguardManager.isKeyguardSecure) {
            notifyUser("Fingerprint has not been enabled in settings.")
            return false
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED) {
            notifyUser("Fingerprint has not been enabled in settings.")
            return false
        }
        return if (packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            true
        } else true
    }
}