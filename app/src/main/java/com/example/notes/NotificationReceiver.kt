package com.example.notes

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.notes.util.Constants

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val notification = NotificationCompat.Builder(context, Constants.channelID)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("TITLE HERE")
            .setContentText("TEXT HERE")
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(Constants.notificationID, notification)

        val intent = Intent(context, NotificationService::class.java)
        context.startService(intent)
    }

    companion object {

    }
}