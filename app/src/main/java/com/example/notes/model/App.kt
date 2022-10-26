package com.example.notes.model

import java.io.Serializable

data class App(
    var id: Long = 0L,
    var title: String? = "",
    var content: String? = "",
    var date: String? = "",
    var fingerPass: Boolean = false

) : Serializable {

}