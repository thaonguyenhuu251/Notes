package com.example.notes.view.components

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.example.notes.R
import com.example.notes.databinding.DialogDayBinding
import java.util.*

class DateDialog : DialogFragment() {
    private lateinit var binding: DialogDayBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogDayBinding.inflate(layoutInflater)
        binding.txtDone.setOnClickListener{
            val day = binding.dayPicker.dayOfMonth
            val year = binding.dayPicker.year
            val month = binding.dayPicker.month + 1
            dismiss()
        }

        binding.txtExit.setOnClickListener{ dismiss() }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dialog = Dialog(requireContext())
        val window = dialog.window
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        setStyle(STYLE_NORMAL, R.style.MyDialog)
        /*val windowAttributes: WindowManager.LayoutParams = window.attributes!!
        windowAttributes.gravity = Gravity.CENTER_VERTICAL
        window.attributes = windowAttributes*/


        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    companion object {
        const val TAG = "DateDialog"
    }
}