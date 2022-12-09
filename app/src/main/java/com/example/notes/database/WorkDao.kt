package com.example.notes.database

import androidx.room.*
import com.example.notes.model.Work
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWork(work: Work)

    @Query("select * from work")
    fun getWork(): Flow<List<Work>>

    @Delete
    fun deleteWork(work: Work)

    @Update
    fun updateWork(work: Work)

}