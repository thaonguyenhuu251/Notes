package com.example.notes.model

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity
import java.io.Serializable

@Entity
data class Work (
    @PrimaryKey
    var idWork: Long? = null,
    @ColumnInfo(name = "nameWork")
    var nameWork: String? = null,
    @ColumnInfo(name = "contentWork")
    var contentWork: String? = null,
    @ColumnInfo(name = "timeNotify")
    var timeNotify: Long? = 0L,
    @ColumnInfo(name = "isTimeNotify")
    var isTimeNotify: Boolean? = false,
)
