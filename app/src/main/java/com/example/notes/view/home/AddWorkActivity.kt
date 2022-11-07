package com.example.notes.view.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.notes.base.BaseActivity
import com.example.notes.database.WorkRoomDatabaseClass
import com.example.notes.databinding.ActivityAddWorkBinding
import com.example.notes.model.Work
import com.example.notes.util.Calendar
import com.example.notes.util.FileUtils
import com.example.notes.util.FileUtils.hideKeyboard
import com.example.notes.util.PreferencesSettings
import com.example.notes.util.SimpleDateFormat
import com.example.notes.view.components.DateDialog
import com.example.notes.view.components.TimeDialog
import kotlinx.coroutines.launch
import java.util.*

class AddWorkActivity : BaseActivity(), DateDialog.OnDone, TimeDialog.OnDone {
    private lateinit var binding: ActivityAddWorkBinding
    private val workDatabase by lazy { WorkRoomDatabaseClass.getDataBase(this).workDao() }

    private var hour: Int = 0
    private var minutes: Int = 0
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setTheme(PreferencesSettings.getBackground(this))
        binding = ActivityAddWorkBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        binding.root.setOnClickListener { hideKeyboard(this) }
        binding.txtBack.setOnClickListener { onBackPressed() }
        binding.edtTimeComplete.setOnClickListener {
            val dialogTime = TimeDialog(this)
            dialogTime.show(supportFragmentManager, dialogTime.tag)
        }
        binding.edtStartDay.setOnClickListener {
            val dialogDate = DateDialog(this)
            dialogDate.show(supportFragmentManager, dialogDate.tag)
        }
        binding.btnAddWork.setOnClickListener {
            try {
                val work = Work()
                work.idWork = System.currentTimeMillis()
                work.nameWork = binding.edtNameWork.text.toString().trim { it <= ' ' }
                work.contentWork = binding.edtContentWork.text.toString().trim { it <= ' ' }
                val calendar = Calendar()
                calendar.set(year , month , day, hour, minutes)
                work.timeNotify = calendar.timeInMillis
                work.isTimeNotify = true

                lifecycleScope.launch {
                    workDatabase.addWork(work)
                }

                Toast.makeText(this, "Data Successfully saved", Toast.LENGTH_SHORT).show()
                binding.edtStartDay.setText("")
                binding.edtNameWork.setText("")
                binding.edtTimeComplete.setText("")
                binding.edtContentWork.setText("")
                onBackPressed()

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
            this.year = SimpleDateFormat("yyyy").format(date).toString().toInt()
            this.month = SimpleDateFormat("MM").format(date).toString().toInt()
            this.day = SimpleDateFormat("dd").format(date).toString().toInt()
        }
    }

    override fun onClickTime(isClick: Boolean, hour: Int, minutes: Int) {
        super.onClickTime(isClick, hour, minutes)
        if (isClick) {
            this.hour = hour
            this.minutes = minutes
            binding.edtTimeComplete.setText(FileUtils.formatTimeNew(hour, minutes))
        }
    }
}