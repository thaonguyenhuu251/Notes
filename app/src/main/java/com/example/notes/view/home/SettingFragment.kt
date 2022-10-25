package com.example.notes.view.home

import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.notes.view.login.LoginPassword
import com.example.notes.databinding.FragmentSettingBinding
import com.example.notes.view.components.LanguageDialog
import kotlin.String as String

class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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