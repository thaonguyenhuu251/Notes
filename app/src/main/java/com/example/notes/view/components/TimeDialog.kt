package com.example.notes.view.components
import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.example.notes.R
import com.example.notes.databinding.DialogTimeBinding
import java.util.*

class TimeDialog : DialogFragment() {
    private lateinit var binding: DialogTimeBinding
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
        binding.txtDone.setOnClickListener{
            val hour = binding.timePicker.currentHour
            val minutes = binding.timePicker.currentMinute.toFloat()
            dismiss()
        }

        binding.txtExit.setOnClickListener{ dismiss() }
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
}