package com.example.notes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notes.model.Note

@Database(entities = [Note::class], version = 2, exportSchema = false)
abstract class NoteRoomTrashDatabase : RoomDatabase() {
    abstract fun noteMarkDao(): NoteMarkDao
    companion object {
        @Volatile
        private var INSTANCE: NoteRoomTrashDatabase? = null
        fun getDataBase(context: Context): NoteRoomTrashDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = buildDataBase(context)
                }
            }

            return INSTANCE!!
        }

        private fun buildDataBase(context: Context): NoteRoomTrashDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                NoteRoomTrashDatabase::class.java,
                "note_trash"
            ).build()
        }
    }
}