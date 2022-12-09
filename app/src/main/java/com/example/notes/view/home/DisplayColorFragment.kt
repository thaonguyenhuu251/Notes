package com.example.notes.view.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.adapter.DisplayColorAdapter
import com.example.notes.databinding.LayoutDisplaySettingsColorBinding
import com.example.notes.util.Constants

class DisplayColorFragment : Fragment() {
    lateinit var colorAdapter: DisplayColorAdapter
    private lateinit var binding: LayoutDisplaySettingsColorBinding
    lateinit var layoutManager: LinearLayoutManager
    private var listColor = mutableListOf<String>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        listColor.add(Constants.COLOR_BLUE)
        listColor.add(Constants.COLOR_RED)
        listColor.add(Constants.COLOR_PINK)
        listColor.add(Constants.COLOR_DARKPINK)
        listColor.add(Constants.COLOR_VIOLET)
        listColor.add(Constants.COLOR_SKYBLUE)
        listColor.add(Constants.COLOR_GREEN)
        listColor.add(Constants.COLOR_GREY)
        listColor.add(Constants.COLOR_BROWN)
    }
}