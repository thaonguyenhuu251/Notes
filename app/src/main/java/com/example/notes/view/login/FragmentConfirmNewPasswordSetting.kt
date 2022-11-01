package com.example.notes.view.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.notes.databinding.ActicityDisplaySettingsBinding.inflate
import com.example.notes.databinding.ActivityAddNoteBinding.inflate
import com.example.notes.databinding.ActivityNewPasswordBinding
import com.example.notes.databinding.CustomNewPasswordBinding
import com.example.notes.databinding.FragmentConfirmNewPasswordBinding
import com.example.notes.util.Constants

class FragmentConfirmNewPasswordSetting : Fragment() {

    private lateinit var binding: FragmentConfirmNewPasswordBinding
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
        binding = FragmentConfirmNewPasswordBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}