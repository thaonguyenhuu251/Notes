package com.example.notes.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.notes.model.Alarm
import com.example.notes.repo.AlarmRepository

class CreateAlarmViewModel(application: Application) : AndroidViewModel(application) {
    private val alarmRepository: AlarmRepository = AlarmRepository(application)
    fun insert(alarm: Alarm) {
        alarmRepository.insert(alarm)
    }

    fun update(alarm: Alarm) {
        alarmRepository.update(alarm)
    }

    fun delete(alarm: Alarm) {
        alarmRepository.delete(alarm)
    }

}
