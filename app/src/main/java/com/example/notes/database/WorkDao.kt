package com.example.notes.database

import androidx.room.*
import com.example.notes.model.Work

@Dao
interface WorkDao {
    @Insert
    fun addWork(work: Work?)

    @get:Query("select * from work")
    val work: List<Work?>?

    @Delete
    fun deleteWork(work: Work?)

    @Update
    fun updateWork(work: Work?)

    @Query("select * from work Where startDay =:startDay")
    fun getWorkByKey(startDay: String?): List<Work?>?

    @Query("select * from work Where idWork =:idWork")
    fun getWorkByKey(idWork: Long): Work?
}