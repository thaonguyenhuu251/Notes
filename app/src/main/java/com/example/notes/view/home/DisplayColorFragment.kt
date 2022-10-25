package com.example.notes.view.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.R
import com.example.notes.adapter.DisplayBackgroundAdapter
import com.example.notes.adapter.DisplayColorAdapter
import com.example.notes.databinding.LayoutDisplaySettingsBackgroundBinding
import com.example.notes.databinding.LayoutDisplaySettingsColorBinding

class DisplayColorFragment:Fragment() {
    lateinit var colorAdapter: DisplayColorAdapter
    private lateinit var binding: LayoutDisplaySettingsColorBinding
    lateinit var layoutManager: LinearLayoutManager
    private var listDarkColor = mutableListOf<Int>()
    private var listLightColor = mutableListOf<Int>()
    private var listTextColor = mutableListOf<Int>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutDisplaySettingsColorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerview.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.layoutManager = layoutManager
        generateItemBackground()
    }

    override fun onResume() {
        super.onResume()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun generateItemBackground() {
        addListDarkColor()
        addListLightColor()
        addListTextColor()

        colorAdapter = DisplayColorAdapter(requireContext(), listDarkColor, listLightColor, listTextColor)
        binding.recyclerview.adapter = colorAdapter
        colorAdapter.notifyDataSetChanged()
    }

    private fun addListTextColor() {
        listTextColor.add(R.string.blue)
        listTextColor.add(R.string.orange)
        listTextColor.add(R.string.yellow)
        listTextColor.add(R.string.turquoise)
    }

    private fun addListLightColor() {
        listLightColor.add(R.color.background_blue_light)
        listLightColor.add(R.color.background_orange_light)
        listLightColor.add(R.color.background_yellow_light)
        listLightColor.add(R.color.background_turquoise_light)
    }

    private fun addListDarkColor() {
        listDarkColor.add(R.color.background_blue_dark)
        listDarkColor.add(R.color.background_orange_dark)
        listDarkColor.add(R.color.background_yellow_dark)
        listDarkColor.add(R.color.background_turquoise_dark)
    }
}