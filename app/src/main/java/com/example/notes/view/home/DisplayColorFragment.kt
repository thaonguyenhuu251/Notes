package com.example.notes.view.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.R
import com.example.notes.adapter.DisplayColorAdapter
import com.example.notes.databinding.LayoutDisplaySettingsColorBinding
import com.example.notes.model.DisplayColor
import com.example.notes.util.Constants

class DisplayColorFragment:Fragment() {
    lateinit var colorAdapter: DisplayColorAdapter
    private lateinit var binding: LayoutDisplaySettingsColorBinding
    lateinit var layoutManager: LinearLayoutManager
    private var listColor = mutableListOf<DisplayColor>()
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
        addListColor()

        colorAdapter = DisplayColorAdapter(requireContext(), listColor)
        binding.recyclerview.adapter = colorAdapter
        colorAdapter.notifyDataSetChanged()
    }

    private fun addListColor() {
        listColor.add(DisplayColor(R.color.blue_dark, R.color.blue_light, R.string.blue))
        listColor.add(DisplayColor(R.color.background_orange_dark, R.color.background_orange_light, R.string.orange))
        listColor.add(DisplayColor(R.color.background_yellow_dark, R.color.background_yellow_light, R.string.yellow))
        listColor.add(DisplayColor(R.color.background_turquoise_dark, R.color.background_turquoise_light, R.string.turquoise))
    }
}