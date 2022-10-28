package com.example.notes.view.home

import com.example.notes.util.FileUtils.hideKeyboard
import androidx.appcompat.app.AppCompatActivity
import android.widget.TimePicker
import android.widget.DatePicker
import android.widget.TextView
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.notes.databinding.ActivityAddWorkBinding
import com.example.notes.util.FileUtils
import com.example.notes.view.components.DateDialog
import com.example.notes.view.components.TimeDialog
import java.lang.Exception

class AddWorkActivity : AppCompatActivity(), DateDialog.OnDone {
    private lateinit var binding: ActivityAddWorkBinding
    var timepicker: TimePicker? = null
    private val datePicker: DatePicker? = null
    var hour = 0
    var minutes = 0f
    var txtDone: TextView? = null
    var txtExit: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWorkBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        binding.root.setOnClickListener { hideKeyboard(this) }
        binding.txtBack.setOnClickListener { onBackPressed() }
        binding.edtTimeComplete.setOnClickListener {
            val dialogTime = TimeDialog()
            dialogTime.show(supportFragmentManager, dialogTime.tag)
        }
        binding.edtStartDay.setOnClickListener {
            val dialogDate = DateDialog(this)
            dialogDate.show(supportFragmentManager, dialogDate.tag)
        }
        binding.btnAddWork.setOnClickListener {
            val timeComplete: Float
            val nameWork: String
            val startDay: String
            val contentWork: String
            try {
                timeComplete = hour + (minutes / 60)
                nameWork = binding.edtNameWork.text.toString().trim { it <= ' ' }
                startDay = binding.edtStartDay.text.toString().trim { it <= ' ' }
                contentWork = binding.edtContentWork.text.toString().trim { it <= ' ' }
                if (timeComplete < 0 || timeComplete > 8) {
                    Toast.makeText(this, "time >0 or time < 8", Toast.LENGTH_SHORT).show()
                    binding.edtTimeComplete.setText("")
                } else {
                    /*Work work = new Work();
                    work.setIdWork(System.currentTimeMillis());
                    work.setNameWork(nameWork);
                    work.setStartDay(startDay);
                    work.setContentWork(contentWork);
                    work.setTimeComplete(timeComplete);
                    MainActivity.roomDatabaseClass.workDao().addWork(work);*/
                    Toast.makeText(this, "Data Successfully saved", Toast.LENGTH_SHORT).show()
                    binding.edtStartDay.setText("")
                    binding.edtNameWork.setText("")
                    binding.edtTimeComplete.setText("")
                    binding.edtContentWork.setText("")
                    onBackPressed()
                }
            } catch (e: Exception) {
                Toast.makeText(this, "do not leave blank", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onClick(isClick: Boolean, date: Long) {
        super.onClick(isClick, date)
        if (isClick) {
            binding.edtStartDay.setText(FileUtils.formatCalendars(date))
        }
    }
}