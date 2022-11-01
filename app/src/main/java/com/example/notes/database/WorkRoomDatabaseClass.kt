package com.example.notes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.example.notes.model.Work
import androidx.room.RoomDatabase

@Database(entities = [Work::class], version = 1, exportSchema = false)
abstract class WorkRoomDatabaseClass : RoomDatabase() {
    abstract fun workDao(): WorkDao
    companion object {
        @Volatile
        private var INSTANCE: WorkRoomDatabaseClass? = null
        fun getDataBase(context: Context): WorkRoomDatabaseClass {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = buildDataBase(context)
                }
            }

            return INSTANCE!!
        }

        private fun buildDataBase(context: Context): WorkRoomDatabaseClass {
            return Room.databaseBuilder(
                context.applicationContext,
                WorkRoomDatabaseClass::class.java,
                "work_database"
            ).build()
        }
    }
}