package com.example.notes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.example.notes.model.Work
import androidx.room.RoomDatabase
import com.example.notes.model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteRoomDatabaseClass : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    companion object {
        @Volatile
        private var INSTANCE: NoteRoomDatabaseClass? = null
        fun getDataBase(context: Context): NoteRoomDatabaseClass {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = buildDataBase(context)
                }
            }

            return INSTANCE!!
        }

        private fun buildDataBase(context: Context): NoteRoomDatabaseClass {
            return Room.databaseBuilder(
                context.applicationContext,
                NoteRoomDatabaseClass::class.java,
                "note_database"
            ).build()
        }
    }
}