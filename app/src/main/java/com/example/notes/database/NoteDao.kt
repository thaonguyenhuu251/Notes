package com.example.notes.database

import androidx.room.*
import com.example.notes.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: Note)

    @Query("select * from note")
    fun getNotes(): Flow<List<Note>>

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)

}