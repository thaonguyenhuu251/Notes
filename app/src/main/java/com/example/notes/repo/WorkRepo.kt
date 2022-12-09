package com.example.notes.repo

import androidx.annotation.WorkerThread
import com.example.notes.database.WorkDao
import com.example.notes.model.Work

class WorkRepo(private val workDao: WorkDao) {
    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    //val allWork: MutableList<Work> = workDao.getWork()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @WorkerThread
    suspend fun insert(work: Work) {
        workDao.addWork(work)
    }

}