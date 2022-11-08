package com.example.notes.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey
    var idNote: Long? = null,
    @ColumnInfo(name = "titleNote")
    var titleNote: String? = null,
    @ColumnInfo(name = "contentNote")
    var contentNote: String? = null,
    @ColumnInfo(name = "timeNotify")
    var timeNotify: Long? = 0L,
    @ColumnInfo(name = "isTimeNotify")
    var isTimeNotify: Boolean? = false,
    @ColumnInfo(name = "isMark")
    var isMark: Boolean? = false,
)