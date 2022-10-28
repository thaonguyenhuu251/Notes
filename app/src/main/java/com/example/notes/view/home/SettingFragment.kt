package com.example.notes.view.home

import android.annotation.SuppressLint
import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.notes.databinding.FragmentSettingBinding
import com.example.notes.util.Constants
import com.example.notes.view.components.LanguageDialog
import com.example.notes.view.login.LoginPassword


class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding
    private lateinit var appPreferences : SharedPreferences

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
        binding = FragmentSettingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    @SuppressLint("ApplySharedPref")
    private fun initView() {
        val isPassword = appPreferences.getBoolean(Constants.PASSWORD, false)
        binding.txtPrivacyPolicy.setOnClickListener {
            val i = Intent(requireContext(), LoginPassword::class.java)
            startActivity(i)
        }
        binding.txtDisplay.setOnClickListener {
            val i = Intent(requireContext(), DisplaySettingsActivity::class.java)
            startActivity(i)
        }

        binding.txtTrash.setOnClickListener {
            val i = Intent(requireContext(), TrashActivity::class.java)
            startActivity(i)
        }

        binding.txtLanguage.setOnClickListener {
            val languageDialog = LanguageDialog()
            languageDialog.show(childFragmentManager, languageDialog.tag)
        }

        if (isPassword) {
            binding.swLoginFingerprint.isChecked = true
        }

        binding.swLoginFingerprint.setOnCheckedChangeListener { buttonView, isChecked ->
            when {
                isChecked -> {
                    if (isPassword) {
                        val editor = appPreferences.edit()
                        editor.putBoolean(Constants.FINGER_ON, true)
                        editor.apply()
                        editor.commit()
                    } else {
                        val editor = appPreferences.edit()
                        editor.putBoolean(Constants.FINGER_ON, false)
                        editor.apply()
                        editor.commit()
                    }
                }
            }
        }

        if (!checkBiometricSupport()) {
            binding.swLoginFingerprint.isEnabled = false
        }

    }

    private fun checkBiometricSupport(): Boolean {
        val keyguardManager : KeyguardManager = requireActivity().getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        if(!keyguardManager.isKeyguardSecure) {
            notifyUser("Fingerprint has not been enabled in settings.")
            return false
        }
        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED) {
            notifyUser("Fingerprint has not been enabled in settings.")
            return false
        }
        return if (requireActivity().packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            true
        } else true
    }

    private fun notifyUser(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}