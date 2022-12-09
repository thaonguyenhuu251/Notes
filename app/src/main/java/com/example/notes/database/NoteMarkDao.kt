package com.example.notes.database

import androidx.room.*
import com.example.notes.model.Note
import com.example.notes.model.Work
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteMarkDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note:Note)

    @Query("select * from note")
    fun getNote(): Flow<List<Note>>

    @Delete
    fun deleteNote(note: Note)

}