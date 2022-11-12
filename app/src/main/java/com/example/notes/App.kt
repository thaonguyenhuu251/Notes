package com.example.notes

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.example.notes.base.BaseActivity
import com.example.notes.util.Constants
import com.example.notes.util.Event
import com.example.notes.util.PreferencesSettings
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.*

class App : Application() {
    companion object {
        val eventBus: PublishSubject<HashMap<String, Any>> by lazy { PublishSubject.create() }
        const val CHANNEL_ID = "ALARM_SERVICE_CHANNEL"
    }
    private var disposable: Disposable? = null

    override fun onCreate() {
        super.onCreate()
        disposable = eventBus.subscribe{
            it[Event.EVENT_CHANGE_BACKGROUND]?.let { data->

            }
        }
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Alarm Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
        }
    }

}