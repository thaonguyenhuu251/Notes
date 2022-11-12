package com.example.notes

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class NotificationService : Service() {

    lateinit var mediaPlayer: MediaPlayer
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaPlayer = MediaPlayer.create(this, R.raw.time_alarm)
        mediaPlayer.start()
        return START_NOT_STICKY
    }

    override fun stopService(name: Intent?): Boolean {
        return super.stopService(name)
    }
//
}