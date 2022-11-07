package com.example.notes.util

import java.text.SimpleDateFormat
import java.util.*

fun SimpleDateFormat(
    pattern: String,
    locale: Locale = Locale.getDefault(),
    timezone: TimeZone = TimeZone()
) = SimpleDateFormat(pattern, locale).apply { timeZone = timezone }

fun Date.add(field: Int, amount: Int): Date {
    Calendar.getInstance().apply {
        time = this@add
        add(field, amount)
        return time
    }
}

fun Date.formatToJPYear(): String{
    val sdf= SimpleDateFormat("yyyy", Locale.getDefault())
    return sdf.format(this)
}

fun Date.formatToJPMonth(): String{
    val sdf= SimpleDateFormat("MM", Locale.getDefault())
    return sdf.format(this)
}

fun Date.formatToJPMonthYear(): String{
    val sdf= SimpleDateFormat("yyyy年M月", Locale.getDefault())
    return sdf.format(this)
}

fun Date.addYears(years: Int): Date{
    return add(Calendar.YEAR, years)
}
fun Date.addMonths(months: Int): Date {
    return add(Calendar.MONTH, months)
}
fun Date.addDays(days: Int): Date{
    return add(Calendar.DAY_OF_MONTH, days)
}

fun Date.startOfYear(): Date {
    val calendar = Calendar()
    calendar.time = this
    calendar.month = 1

    return calendar.time
}

fun Date.startOfMonth(year: Int, month: Int): Date {
    val calendar = Calendar()
    calendar.set(year , month - 1, 1,0,0,0)
    return calendar.time
}

fun Date.endOfMonth(year: Int, month: Int): Date {
    val calendar = Calendar()
    calendar.set(year , month,1,0,0,0)
    calendar.add(Calendar.MONTH, 1);
    calendar.add(Calendar.MILLISECOND, -1);
    return calendar.time
}


