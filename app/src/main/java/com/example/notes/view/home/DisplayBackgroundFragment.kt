package com.example.notes.view.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SnapHelper
import com.example.notes.R
import com.example.notes.adapter.DisplayBackgroundAdapter
import com.example.notes.adapter.WorkDoAdapter
import com.example.notes.databinding.LayoutDisplaySettingsBackgroundBinding

class DisplayBackgroundFragment:Fragment() {
    lateinit var backgroundAdapter: DisplayBackgroundAdapter
    private lateinit var binding:LayoutDisplaySettingsBackgroundBinding
    lateinit var layoutManager: GridLayoutManager
    private var listBackground = mutableListOf<Int>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutDisplaySettingsBackgroundBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerview.setHasFixedSize(true)
        layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerview.layoutManager = layoutManager
        generateItemBackground()
    }

    override fun onResume() {
        super.onResume()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun generateItemBackground() {
        listBackground.add(R.drawable.bg_purple_sky)
        listBackground.add(R.drawable.bg_moon_night)
        listBackground.add(R.drawable.bg_lofi_chill_night)
        listBackground.add(R.drawable.bg_violet_evergarden)


        backgroundAdapter = DisplayBackgroundAdapter(requireContext(), listBackground)
        binding.recyclerview.adapter = backgroundAdapter
        backgroundAdapter.notifyDataSetChanged()
    }
}