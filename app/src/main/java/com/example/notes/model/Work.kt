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
    @ColumnInfo(name = "startDay")
    var startDay: String? = null,
    @ColumnInfo(name = "timeComplete")
    var timeComplete: Float = 0f
)
