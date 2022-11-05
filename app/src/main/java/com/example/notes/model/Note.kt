package com.example.notes.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class Note(
  @PrimaryKey
  @ColumnInfo(name = "id")
  var id: Long = 0L,
  var title: String? = "",
  var content: String? = "",
  var date: Float = 0f,
  var colorBackground: String = "blue"

)
