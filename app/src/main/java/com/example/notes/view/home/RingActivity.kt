package com.example.notes.view.home

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notes.databinding.ActivityRingBinding
import com.example.notes.model.Alarm
import com.example.notes.receiver.AlarmBroadcastReceiver
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
        setListeners()
        setValueAlarm()
    }

    @SuppressLint("SimpleDateFormat")
    private fun setValueAlarm() {
        val hour = SimpleDateFormat("HH").format(Date())
        val minute = SimpleDateFormat("mm").format(Date())
        binding.date.text = "$hour:$minute"
    }

    private fun setListeners() {
        binding.ringDismiss.setOnClickListener {
            val intentService = Intent(applicationContext, AlarmService::class.java)
            applicationContext.stopService(intentService)
            finish()
        }
        binding.ringDetail.setOnClickListener {
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
