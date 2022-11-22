package com.example.notes.view.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.notes.databinding.ActivityRingBinding
import com.example.notes.model.Alarm
import com.example.notes.receiver.AlarmBroadcastReceiver.Companion.CONTENT
import com.example.notes.receiver.AlarmBroadcastReceiver.Companion.TITLE
import com.example.notes.servive.AlarmService
import com.example.notes.util.Constants
import java.text.SimpleDateFormat
import java.util.*

class RingActivity : AppCompatActivity() {

    lateinit var binding: ActivityRingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val _title = intent.getStringExtra(TITLE)
        val _content = intent.getStringExtra(CONTENT)
        binding.txtTitle.setText(_title)
        binding.txtContent.setText(_content)
        setListeners()
        setValueAlarm()
    }

    override fun onResume() {
        super.onResume()
    }
    @SuppressLint("SimpleDateFormat")
    private fun setValueAlarm() {
        val hour = SimpleDateFormat("HH").format(Date())
        val minute = SimpleDateFormat("mm").format(Date())
        binding.time.text = "$hour:$minute"
    }

    private fun setListeners() {
        binding.btnRingDismiss.setOnClickListener {
            val intentService = Intent(applicationContext, AlarmService::class.java)
            applicationContext.stopService(intentService)
            finish()
        }
        binding.btnRingDetail.setOnClickListener {
            val intentService = Intent(applicationContext, AlarmService::class.java)
            applicationContext.stopService(intentService)
            val i = Intent(this, ListWorkFragment::class.java)
            startActivity(i)
            finish()
        }
        binding.activityRingSnooze.setOnClickListener {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = System.currentTimeMillis()
            calendar.add(Calendar.MINUTE, 10)
            val alarm = Alarm(
                alarmId = Random().nextInt(Int.MAX_VALUE),
                time = calendar.timeInMillis,
                title = intent.getStringExtra(TITLE),
                content = intent.getStringExtra(CONTENT),
                created = System.currentTimeMillis(),
                started = true,
                recurring = false,
                isVibration = false
            )

            alarm.schedule(applicationContext)
            val intentService = Intent(applicationContext, AlarmService::class.java)
            applicationContext.stopService(intentService)
            finish()
        }
    }

}
