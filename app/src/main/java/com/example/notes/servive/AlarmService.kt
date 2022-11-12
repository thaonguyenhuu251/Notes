package com.example.notes.servive

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.os.Vibrator
import androidx.core.app.NotificationCompat
import com.example.notes.App.Companion.CHANNEL_ID
import com.example.notes.R
import com.example.notes.receiver.AlarmBroadcastReceiver.Companion.CONTENT
import com.example.notes.receiver.AlarmBroadcastReceiver.Companion.TITLE
import com.example.notes.receiver.AlarmBroadcastReceiver.Companion.VIBRATION
import com.example.notes.view.home.RingActivity

class AlarmService : Service() {
    private var mediaPlayer: MediaPlayer? = null
    private var vibrator: Vibrator? = null
    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm)
        mediaPlayer!!.isLooping = true
        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val booleanExtra = intent.getBooleanExtra(VIBRATION, true)
        val notificationIntent = Intent(this, RingActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        val alarmTitle = intent.getStringExtra(TITLE)
        val alarmContent = intent.getStringExtra(CONTENT)
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(alarmTitle)
            .setContentText(alarmContent)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentIntent(pendingIntent)
            .build()
        mediaPlayer!!.start()
        if (booleanExtra) {
            val pattern = longArrayOf(0, 100, 1000)
            vibrator!!.vibrate(pattern, 0)
        }

        startForeground(1, notification)
        return START_STICKY

    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer!!.stop()
        vibrator!!.cancel()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}
