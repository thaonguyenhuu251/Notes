package com.example.notes.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notes.model.Alarm
import com.example.notes.model.Work
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {
    @Insert
    fun insert(alarm: Alarm)

    @Query("DELETE FROM alarm_table")
    fun deleteAll()

    @get:Query("SELECT * FROM alarm_table ORDER BY created ASC")
    val alarms: LiveData<List<Alarm>>

    @Query("select * from alarm_table")
    fun getAlarm(): Flow<List<Alarm>>

    @Update
    fun update(alarm: Alarm)

    @Delete
    fun deleteAlarm(alarm: Alarm)
}