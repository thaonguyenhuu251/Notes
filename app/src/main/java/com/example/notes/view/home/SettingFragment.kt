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
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.notes.adapter.LoginPasswordExpandableAdapter
import com.example.notes.databinding.FragmentSettingBinding
import com.example.notes.util.Constants
import com.example.notes.view.components.LanguageDialog
import com.example.notes.view.components.ViewModeDialog


class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding
    private lateinit var appPreferences: SharedPreferences

    //expandable list view
    private lateinit var loginPassAdapter: LoginPasswordExpandableAdapter
    private var groupList: MutableList<String> = ArrayList()
    private var childList : MutableList<MutableList<String>> = ArrayList()

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

        //expandable list view
        showChildList()
        loginPassAdapter = LoginPasswordExpandableAdapter(requireContext(), groupList, childList)
        binding.expandableListView.setAdapter(loginPassAdapter)

        binding.expandableListView.setOnGroupClickListener { expandableListView, view, i, l ->
            setListViewHeight(expandableListView, i)
            false
        }
        val isPassword = appPreferences.getBoolean(Constants.PASSWORD, false)

        binding.txtPrivacyPolicy.setOnClickListener {
            val i = Intent(requireContext(), NewPasswordSettingActivity::class.java)
            startActivity(i)
        }
        binding.txtDisplay.setOnClickListener {
            val i = Intent(requireContext(), DisplaySettingsActivity::class.java)
            startActivity(i)
        }

        binding.txtTrash.setOnClickListener {
            val i = Intent(requireContext(), NewPasswordSettingActivity::class.java)
            startActivity(i)
        }

        binding.txtLanguage.setOnClickListener {
            val languageDialog = LanguageDialog()
            languageDialog.show(childFragmentManager, languageDialog.tag)
        }

        /*binding.txtLoginPassword.setOnClickListener {
            val i = Intent(context, LoginPasswordSettingActivity::class.java)
            startActivity(i)
        }*/

        binding.txtViewMode.setOnClickListener {
            val viewmodeDialog = ViewModeDialog()
            viewmodeDialog.show(childFragmentManager, viewmodeDialog.tag)
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
            binding.lnLoginFingerprint.visibility = View.GONE
        }

    }
    private fun showChildList() {
        groupList.add("Login Password")

        val child = mutableListOf<String>()
        child.add("Set Password")
        child.add("Change Password")
        child.add("Delete Password")

        childList.add(child)

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

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}