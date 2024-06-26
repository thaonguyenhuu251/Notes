package com.example.notes.view.components

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.notes.R
import com.example.notes.databinding.DialogTimeBinding
import java.util.*

class TimeDialog(private var onDone: OnDone) : DialogFragment() {
    private lateinit var binding: DialogTimeBinding
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dialog = Dialog(requireContext())
        val window = dialog.window
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        /*val windowAttributes: WindowManager.LayoutParams = window.attributes!!
        windowAttributes.gravity = Gravity.CENTER_VERTICAL
        window.attributes = windowAttributes*/

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        binding = DialogTimeBinding.inflate(layoutInflater)
        var hour = binding.timePicker.hour
        var minutes = binding.timePicker.minute
        binding.timePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
            hour = hourOfDay
            minutes = minute
        }
        binding.txtDone.setOnClickListener {
            onDone.onClickTime(true, hour, minutes)
            dismiss()

        }

        binding.txtExit.setOnClickListener { dismiss() }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.MyDialog)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        const val TAG = "TimeDialog"
    }

    interface OnDone {
        fun onClickTime(isClick: Boolean, hour: Int, minutes: Int) {

        }
    }
}