package com.example.notes.database

import androidx.room.Database
import com.example.notes.model.Work
import androidx.room.RoomDatabase
import com.example.notes.database.WorkDao

@Database(entities = [Work::class], version = 2)
abstract class WorkRoomDatabaseClass : RoomDatabase() {
    abstract fun workDao(): WorkDao?
}