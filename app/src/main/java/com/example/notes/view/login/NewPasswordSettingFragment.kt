package com.example.notes.view.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.notes.R
import com.example.notes.databinding.FragmentHomeBinding
import com.example.notes.databinding.FragmentNewPasswordSettingBinding
import com.example.notes.util.Constants
import com.kevalpatel.passcodeview.PinView
import com.kevalpatel.passcodeview.authenticator.PasscodeViewPinAuthenticator
import com.kevalpatel.passcodeview.indicators.CircleIndicator
import com.kevalpatel.passcodeview.keys.KeyNamesBuilder
import com.kevalpatel.passcodeview.keys.RoundKey

class NewPasswordSettingFragment : Fragment() {
    var array: IntArray? = null
    private lateinit var binding: FragmentNewPasswordSettingBinding
    private lateinit var appPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appPreferences = requireContext().getSharedPreferences(
            Constants.SHARED_PREFERENCES_APP,
            Context.MODE_PRIVATE
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNewPasswordSettingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*val correctPattern = intArrayOf(1, 2, 3, 4, 5)
        binding.patternView.pinAuthenticator = PasscodeViewPinAuthenticator(correctPattern)*/

        binding.patternView.pinLength = 4
        //set number
        binding.patternView.setKey(
            RoundKey.Builder(binding.patternView)
                .setKeyPadding(R.dimen.key_padding)
                .setKeyStrokeColorResource(R.color.white)
                .setKeyStrokeWidth(R.dimen.key_stroke_width)
                .setKeyTextColorResource(R.color.white)
                .setKeyTextSize(R.dimen.key_text_size)
        )

        //set key
        binding.patternView.setIndicator(
            CircleIndicator.Builder(binding.patternView)
                .setIndicatorRadius(R.dimen.indicator_radius)
                .setIndicatorStrokeColorResource(R.color.white)
                .setIndicatorStrokeWidth(R.dimen.indicator_stroke_width)
        )

        binding.patternView.setKeyNames(
            KeyNamesBuilder()
                .setKeyOne(requireContext(), R.string.key_1)
                .setKeyTwo(requireContext(), R.string.key_2)
                .setKeyThree(requireContext(), R.string.key_3)
                .setKeyFour(requireContext(), R.string.key_4)
                .setKeyFive(requireContext(), R.string.key_5)
                .setKeySix(requireContext(), R.string.key_6)
                .setKeySeven(requireContext(), R.string.key_7)
                .setKeyEight(requireContext(), R.string.key_8)
                .setKeyNine(requireContext(), R.string.key_9)
                .setKeyZero(requireContext(), R.string.key_0)
        )
        binding.patternView.title = "Enter the PIN"

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putIntArray(ARG_CURRENT_PIN, binding.patternView.currentTypedPin)
    }

    companion object {
        private const val ARG_CURRENT_PIN = "current_pin"
    }
}