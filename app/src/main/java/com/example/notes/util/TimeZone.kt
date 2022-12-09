package com.example.notes.util

import java.util.*

fun TimeZone(id: String = "GMT+07:00"): TimeZone =
    TimeZone.getTimeZone(id)
