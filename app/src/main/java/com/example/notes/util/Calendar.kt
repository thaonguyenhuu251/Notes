package com.example.notes.util
import java.util.*

fun Calendar(timezone: TimeZone = TimeZone(), locale: Locale = Locale.JAPAN): Calendar =
    Calendar.getInstance(timezone, locale)

fun Calendar(
    year: Int,
    month: Int,
    day: Int,
    timezone: TimeZone = TimeZone(),
    locale: Locale = Locale.CHINESE
): Calendar {
    val calendar = Calendar.getInstance(timezone, locale)
    calendar.year = year
    calendar.month = month
    calendar.day = day
    return calendar
}

fun Calendar(
    time: Long,
    timezone: TimeZone = TimeZone(),
    locale: Locale = Locale.CHINESE
): Calendar {
    val calendar = Calendar.getInstance(timezone, locale)
    calendar.timeInMillis = time
    return calendar
}

var Calendar.year
    get() = get(Calendar.YEAR)
    set(value) = set(Calendar.YEAR, value)

var Calendar.month
    get() = get(Calendar.MONTH) + 1
    set(value) = set(Calendar.MONTH, value - 1)

var Calendar.day
    get() = get(Calendar.DAY_OF_MONTH)
    set(value) = set(Calendar.DAY_OF_MONTH, value)

var Calendar.hour
    get() = get(Calendar.HOUR_OF_DAY)
    set(value) = set(Calendar.HOUR_OF_DAY, value)

var Calendar.minute
    get() = get(Calendar.MINUTE)
    set(value) = set(Calendar.MINUTE, value)

var Calendar.second
    get() = get(Calendar.SECOND)
    set(value) = set(Calendar.SECOND, value)

var Calendar.millisecond
    get() = get(Calendar.MILLISECOND)
    set(value) = set(Calendar.MILLISECOND, value)

val Calendar.dateInMillis: Long
    get() {
        hour = 0
        minute = 0
        second = 0
        millisecond = 0
        return timeInMillis
    }
