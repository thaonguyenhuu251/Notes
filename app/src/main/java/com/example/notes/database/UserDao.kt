package com.example.notes.database

import androidx.room.*
import com.example.notes.model.UserProfile

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertuser(userProfile: UserProfile)

    @Update
    fun update(userProfile: UserProfile)

    @Query("select * from user")
    fun getUser(): List<UserProfile>
}