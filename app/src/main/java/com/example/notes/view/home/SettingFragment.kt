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
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.notes.R
import com.example.notes.adapter.LoginPasswordExpandableAdapter
import com.example.notes.databinding.FragmentSettingBinding
import com.example.notes.util.Constants
import com.example.notes.util.PreferencesSettings
import com.example.notes.view.components.LanguageDialog
import com.example.notes.view.components.ViewModeDialog
import com.example.notes.view.login.ConfirmChangePasswordActivity
import com.example.notes.view.login.ConfirmDeletePasswordActivity
import com.example.notes.view.login.NewPasswordSettingActivity


class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding

    private lateinit var loginPassAdapter: LoginPasswordExpandableAdapter
    private lateinit var groupList: Array<String>
    private lateinit var childList : Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        groupList = resources.getStringArray(R.array.string_sub)
        childList = resources.getStringArray(R.array.string_list)
        initView()
    }

    override fun onResume() {
        showInitialSettings()
        handleSettingsChanged()
        super.onResume()
    }

    private fun showInitialSettings() {
        binding.swDarkMode.isChecked = PreferencesSettings.getThemes(requireContext())
    }

    private fun handleSettingsChanged() {
        binding.swDarkMode.setOnCheckedChangeListener { buttonView, isChecked ->
            PreferencesSettings.setThemes(requireContext(), isChecked)
            if (isChecked) {
                PreferencesSettings.setTheme(requireContext(), MODE_NIGHT_FOLLOW_SYSTEM)
            } else {
                PreferencesSettings.setTheme(requireContext(), AppCompatDelegate.MODE_NIGHT_NO)
            }
            PreferencesSettings.applyTheme(PreferencesSettings.getTheme(requireContext()))
        }
    }

    @SuppressLint("ApplySharedPref")
    private fun initView() {
        loginPassAdapter = LoginPasswordExpandableAdapter(requireContext(), groupList, childList)
        binding.expandableListView.setAdapter(loginPassAdapter)

        binding.expandableListView.setOnGroupClickListener { expandableListView, view, i, l ->
            setListViewHeight(expandableListView, i)
            false
        }

        binding.expandableListView.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            when (childPosition) {
                0 -> {
                    val i = Intent(requireContext(), NewPasswordSettingActivity::class.java)
                    startActivity(i)
                }

                1 -> {
                    val i = Intent(requireContext(), ConfirmChangePasswordActivity::class.java)
                    startActivity(i)
                }
                2 -> {
                    val i = Intent(requireContext(), ConfirmDeletePasswordActivity::class.java)
                    startActivity(i)
                }
            }
            false
        }

        binding.txtPrivacyPolicy.setOnClickListener {

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

        binding.txtViewMode.setOnClickListener {
            val viewmodeDialog = ViewModeDialog()
            viewmodeDialog.show(childFragmentManager, viewmodeDialog.tag)
        }

        binding.swLoginFingerprint.isChecked = PreferencesSettings.getFinger(requireContext())

        binding.swLoginFingerprint.setOnCheckedChangeListener { buttonView, isChecked ->
            when {
                isChecked -> {
                    if (PreferencesSettings.getCode(requireContext())?.isEmpty() == false) {
                        PreferencesSettings.saveToFinger(requireContext(), true)
                        Toast.makeText(requireContext(), getString(R.string.login_finger_success), Toast.LENGTH_SHORT).show()
                    } else {
                        PreferencesSettings.saveToFinger(requireContext(), false)
                        Toast.makeText(requireContext(), getString(R.string.register_password), Toast.LENGTH_SHORT).show()
                        binding.swLoginFingerprint.isChecked = false
                    }
                }

                !isChecked -> {
                    PreferencesSettings.saveToFinger(requireContext(), false)
                }
            }
        }

        if (!checkBiometricSupport()) {
            binding.swLoginFingerprint.isEnabled = false
            binding.lnLoginFingerprint.visibility = View.GONE
        }

    }

    private fun checkBiometricSupport(): Boolean {
        val keyguardManager: KeyguardManager =
            requireActivity().getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        if (!keyguardManager.isKeyguardSecure) {
            //notifyUser("Fingerprint has not been enabled in settings.")
            return false
        }
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.USE_BIOMETRIC
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //notifyUser("Fingerprint has not been enabled in settings.")
            return false
        }
        return if (requireActivity().packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            true
        } else true
    }

    private fun notifyUser(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun setListViewHeight(
        listView: ExpandableListView,
        group: Int
    ) {
        val listAdapter = listView.expandableListAdapter as ExpandableListAdapter
        var totalHeight = 0
        val desiredWidth: Int = View.MeasureSpec.makeMeasureSpec(
            listView.width,
            View.MeasureSpec.EXACTLY
        )
        for (i in 0 until listAdapter.groupCount) {
            val groupItem: View = listAdapter.getGroupView(i, false, null, listView)
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
            totalHeight += groupItem.measuredHeight
            if (listView.isGroupExpanded(i) && i != group
                || !listView.isGroupExpanded(i) && i == group
            ) {
                for (j in 0 until listAdapter.getChildrenCount(i)) {
                    val listItem: View = listAdapter.getChildView(
                        i, j, false, null,
                        listView
                    )
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
                    totalHeight += listItem.measuredHeight
                }
            }
        }
        val params = listView.layoutParams
        var height = (totalHeight
                + listView.dividerHeight * (listAdapter.groupCount - 1))
        if (height < 10) height = 200
        params.height = height
        listView.layoutParams = params
        listView.requestLayout()
    }

}