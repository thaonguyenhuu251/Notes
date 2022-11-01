package com.example.notes.model

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity
import java.io.Serializable

@Entity
data class Work (
    @PrimaryKey
    var idWork: Long? = null,

    var nameWork: String? = null,

    var contentWork: String? = null,

    var startDay: String? = null,

    var timeComplete: Float = 0f
)
