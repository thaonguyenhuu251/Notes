package com.example.notes.view.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.notes.R
import com.example.notes.databinding.CustomConfirmNewPasswordBinding
import com.example.notes.view.home.SettingFragment
import com.kevalpatel.passcodeview.PinView
import com.kevalpatel.passcodeview.indicators.CircleIndicator
import com.kevalpatel.passcodeview.keys.KeyNamesBuilder
import com.kevalpatel.passcodeview.keys.RoundKey

class ConfirmNewPassword : AppCompatActivity() {
    private lateinit var binding: CustomConfirmNewPasswordBinding
    lateinit var mPinView: PinView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CustomConfirmNewPasswordBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        mPinView = binding.patternView
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


        mPinView.pinLength = PinView.DYNAMIC_PIN_LENGTH


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

        binding.txtConfirm.setOnClickListener {
            val i = Intent(this, SettingFragment::class.java)
            startActivity(i)
            finish()
        }
    }
}