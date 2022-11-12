package com.example.notes.repo

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.notes.database.AlarmDao
import com.example.notes.database.AlarmDatabase
import com.example.notes.model.Alarm

class AlarmRepository(application: Application?) {
    private var alarmDao: AlarmDao
    private var alarmsLiveData: LiveData<List<Alarm>>
    fun insert(alarm: Alarm) {
        AlarmDatabase.databaseWriteExecutor.execute { alarmDao.insert(alarm) }
    }

    fun update(alarm: Alarm) {
        AlarmDatabase.databaseWriteExecutor.execute { alarmDao.update(alarm) }
    }

    fun delete(alarm: Alarm) {
        AlarmDatabase.databaseWriteExecutor.execute { alarmDao.deleteAlarm(alarm) }
    }

    fun getAlarmsLiveData(): LiveData<List<Alarm>> {
        return alarmsLiveData
    }

    init {
        val db: AlarmDatabase = AlarmDatabase.getDatabase(application!!.applicationContext)!!
        alarmDao = db.alarmDao()
        alarmsLiveData = alarmDao.alarms
    }
}
