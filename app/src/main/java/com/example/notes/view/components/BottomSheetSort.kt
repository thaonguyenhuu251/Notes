package com.example.notes.view.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.notes.R
import com.example.notes.databinding.LayoutBottomsheetSortBinding
import com.example.notes.util.Event
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetSort : BottomSheetDialogFragment() {
    private lateinit var binding: LayoutBottomsheetSortBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutBottomsheetSortBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtNameAc.setOnClickListener {
            Event.eventSortNameAscending()
            dismiss()
        }

        binding.txtTimeCreate.setOnClickListener {
            Event.eventSortTimeCreate()
            dismiss()
        }

        binding.txtTimeOpen.setOnClickListener {
            Event.eventSortTimeOpen()
            dismiss()
        }

        binding.txtTimeAc.setOnClickListener {
            Event.eventSortTimeAscending()
            dismiss()
        }

        binding.txtTimeDc.setOnClickListener {
            Event.eventSortTimeDecrease()
            dismiss()
        }
    }

    interface OnClickSort {
        fun onClick(sortType: String)
    }

    companion object {
        const val TAG = "BottomSheetSort"

    }

}