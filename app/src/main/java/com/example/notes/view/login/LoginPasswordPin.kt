package com.example.notes.view.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notes.R
import com.example.notes.databinding.ActivityLoginPasswordBinding
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPasswordBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        mPinView = binding.patternView
        val correctPattern = intArrayOf(1, 2, 3, 4)
        mPinView.setPinAuthenticator(PasscodeViewPinAuthenticator(correctPattern))

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
        mPinView.setPinLength(PinView.DYNAMIC_PIN_LENGTH)


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
        mPinView.setTitle("Enter the PIN")
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
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putIntArray(ARG_CURRENT_PIN, mPinView!!.currentTypedPin)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        mPinView!!.currentTypedPin = savedInstanceState.getIntArray(ARG_CURRENT_PIN)
    }

    companion object {
        private const val ARG_CURRENT_PIN = "current_pin"
    }
}