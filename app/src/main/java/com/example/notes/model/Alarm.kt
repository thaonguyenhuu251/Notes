package com.example.notes.model

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.TextView
import android.widget.Toast
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notes.receiver.AlarmBroadcastReceiver
import com.example.notes.receiver.AlarmBroadcastReceiver.Companion.CONTENT
import com.example.notes.receiver.AlarmBroadcastReceiver.Companion.RECURRING
import com.example.notes.receiver.AlarmBroadcastReceiver.Companion.TITLE
import com.example.notes.receiver.AlarmBroadcastReceiver.Companion.VIBRATION
import com.example.notes.util.SimpleDateFormat
import java.util.*

@Entity(tableName = "alarm_table")
class Alarm {
    @PrimaryKey
    var alarmId = 0
    var time: Long? = 0L
        private set
    var isStarted = false
        private set
    var isVibration = false
        private set
    var isRecurring = false
        private set
    var title: String? = null
        private set
    var content: String? = null
        private set
    var created: Long = 0

    constructor(
        alarmId: Int,
        time: Long?,
        title: String?,
        content: String?,
        created: Long,
        started: Boolean,
        recurring: Boolean,
        isVibration: Boolean,
    ) {
        this.alarmId = alarmId
        this.time = time
        isStarted = started
        this.isVibration = isVibration
        isRecurring = recurring
        this.title = title
        this.content = content
        this.created = created
    }

    constructor(
        alarmId: Int,
        value: Int,
        value1: Int,
        desc: TextView?,
        created: Long,
        started: Boolean,
        recurring: Boolean,
        monday: Boolean?,
        tuesday: Boolean?,
        wednesday: Boolean?,
        thursday: Boolean?,
        friday: Boolean?,
        saturday: Boolean?,
        sunday: Boolean?
    ) {
    }

    @SuppressLint("DefaultLocale")
    fun schedule(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        intent.putExtra(RECURRING, isRecurring)
        intent.putExtra(TITLE, title)
        intent.putExtra(CONTENT, content)
        intent.putExtra(VIBRATION, isVibration)
        val alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        /*calendar[Calendar.YEAR] = SimpleDateFormat("YY", Locale.getDefault()).format(Date(time ?: 0)).toInt()
        calendar[Calendar.MONTH] = SimpleDateFormat("MM", Locale.getDefault()).format(Date(time ?: 0)).toInt()
        calendar[Calendar.DAY_OF_MONTH] = SimpleDateFormat("dd", Locale.getDefault()).format(Date(time ?: 0)).toInt()*/
        calendar[Calendar.HOUR_OF_DAY] = SimpleDateFormat("HH", Locale.getDefault()).format(Date(time ?: 0)).toInt()
        calendar[Calendar.MINUTE] = SimpleDateFormat("mm", Locale.getDefault()).format(Date(time ?: 0)).toInt()
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0

        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar[Calendar.DAY_OF_MONTH] = calendar[Calendar.DAY_OF_MONTH] + 1
        }
        if (!isRecurring) {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                alarmPendingIntent
            )
        } else {
            val RUN_DAILY = (24 * 60 * 60 * 1000).toLong()
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                RUN_DAILY,
                alarmPendingIntent
            )
        }
        isStarted = true
    }

    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        val alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0)
        alarmManager.cancel(alarmPendingIntent)
        isStarted = false
    }

    val recurringDaysText: String?
        get() {
            if (!isRecurring) {
                return null
            }
            val days = ""
            return days
        }
}
