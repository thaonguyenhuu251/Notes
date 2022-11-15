package com.example.notes.repo

import android.app.Application
import androidx.annotation.WorkerThread
import com.example.notes.database.UserDao
import com.example.notes.database.UserDatabase
import com.example.notes.model.UserProfile

class UserRepo(application: Application?) {

    private var userDao: UserDao

    @WorkerThread
    suspend fun insert(userProfile: UserProfile) {
        userDao.insertuser(userProfile)
    }
    fun update(userProfile: UserProfile) {
        userDao.update(userProfile)
    }


    init {
        val db: UserDatabase = UserDatabase.getDataBase(application!!.applicationContext)
        userDao = db.userDao()
    }
}