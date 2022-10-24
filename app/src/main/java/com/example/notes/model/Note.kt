package com.example.notes.model

import java.io.Serializable

data class Note(
  var id: Long = 0L,
  var title: String? = "",
  var content: String? = "",
  var date: String? = "",
  var colorBackground: String = "blue"

) : Serializable {

}