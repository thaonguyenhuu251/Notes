package com.example.notes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notes.model.Work

@Database(entities = [Work::class], version = 2, exportSchema = false)
abstract class WorkRoomMarkDatabase : RoomDatabase() {
    abstract fun workMarkDao(): WorkMarkDao
    companion object {
        @Volatile
        private var INSTANCE: WorkRoomMarkDatabase? = null
        fun getDataBase(context: Context): WorkRoomMarkDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = buildDataBase(context)
                }
            }

            return INSTANCE!!
        }

        private fun buildDataBase(context: Context): WorkRoomMarkDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                WorkRoomMarkDatabase::class.java,
                "work_database"
            ).build()
        }
    }
}