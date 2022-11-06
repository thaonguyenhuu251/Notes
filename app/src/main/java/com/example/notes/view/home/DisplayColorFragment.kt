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
import com.example.notes.model.DisplayColor
import com.example.notes.util.Constants
import com.example.notes.util.Methods

class DisplayColorFragment : Fragment() {
    lateinit var colorAdapter: DisplayColorAdapter
    private lateinit var binding: LayoutDisplaySettingsColorBinding
    lateinit var layoutManager: LinearLayoutManager
    private var listColor = mutableListOf<DisplayColor>()
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
        listColor.add(
            DisplayColor(
                Methods.getColorDark(Constants.COLOR_BLUE),
                Methods.getColorLight(Constants.COLOR_BLUE),
                Methods.getColorString(Constants.COLOR_BLUE)
            )
        )
        listColor.add(
            DisplayColor(
                Methods.getColorDark(Constants.COLOR_RED),
                Methods.getColorLight(Constants.COLOR_RED),
                Methods.getColorString(Constants.COLOR_RED)
            )
        )
        listColor.add(
            DisplayColor(
                Methods.getColorDark(Constants.COLOR_PINK),
                Methods.getColorLight(Constants.COLOR_PINK),
                Methods.getColorString(Constants.COLOR_PINK)
            )
        )
        listColor.add(
            DisplayColor(
                Methods.getColorDark(Constants.COLOR_DARKPINK),
                Methods.getColorLight(Constants.COLOR_DARKPINK),
                Methods.getColorString(Constants.COLOR_DARKPINK)
            )
        )
        listColor.add(
            DisplayColor(
                Methods.getColorDark(Constants.COLOR_VIOLET),
                Methods.getColorLight(Constants.COLOR_VIOLET),
                Methods.getColorString(Constants.COLOR_VIOLET)
            )
        )
        listColor.add(
            DisplayColor(
                Methods.getColorDark(Constants.COLOR_SKYBLUE),
                Methods.getColorLight(Constants.COLOR_SKYBLUE),
                Methods.getColorString(Constants.COLOR_SKYBLUE)
            )
        )
        listColor.add(
            DisplayColor(
                Methods.getColorDark(Constants.COLOR_GREEN),
                Methods.getColorLight(Constants.COLOR_GREEN),
                Methods.getColorString(Constants.COLOR_GREEN)
            )
        )
        listColor.add(
            DisplayColor(
                Methods.getColorDark(Constants.COLOR_GREY),
                Methods.getColorLight(Constants.COLOR_GREY),
                Methods.getColorString(Constants.COLOR_GREY)
            )
        )
        listColor.add(
            DisplayColor(
                Methods.getColorDark(Constants.COLOR_BROWN),
                Methods.getColorLight(Constants.COLOR_BROWN),
                Methods.getColorString(Constants.COLOR_BROWN)
            )
        )
    }
}