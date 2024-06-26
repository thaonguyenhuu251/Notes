package com.example.notes.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.text.SimpleDateFormat
import kotlin.math.roundToInt

object FileUtils {
    fun formatTime(time: Float): String {
        val hour = time.toInt()
        val min = ((time - hour) * 60 * 10 / 10).roundToInt()
        val minString: String = if (min < 10) {
            "0$min"
        } else {
            min.toString()
        }
        val hourString: String = if (hour < 10) {
            "0$hour"
        } else {
            hour.toString()
        }
        return "$hourString:$minString"
    }

    fun formatTimeNew(hour: Int, min: Int): String {
        /*val minString: String = if (min < 10) {
            "0$min"
        } else {
            min.toString()
        }
        val hourString: String = if (hour < 10) {
            "0$hour"
        } else {
            hour.toString()
        }*/
        return "$hour:$min"
    }

    fun formatTimeFloat(hour: Int, minutes: Int, time: Float) {
        var hour = hour
        var minutes = minutes
        hour = time.toInt()
        minutes = ((time - hour) * 60).toInt()
    }

    fun isPrimeNumber(n: Int): Boolean {
        if (n < 2) {
            return false
        }
        val squareRoot = Math.sqrt(n.toDouble()).toInt()
        for (i in 2..squareRoot) {
            if (n % i == 0) {
                return false
            }
        }
        return true
    }

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun formatDay(day: Int): String {
        return if (day < 10) {
            "0$day"
        } else {
            day.toString()
        }
    }

    fun formatMonth(month: Int): String {
        return if (month < 10) {
            "0$month"
        } else {
            month.toString()
        }
    }

    @SuppressLint("DefaultLocale")
    fun formatCalendar(day: Int, month: Int, year: Int): String {
        val dayString: String = if (day < 10) {
            "0$day"
        } else {
            day.toString()
        }
        val monthString: String = if (month < 10) {
            "0$month"
        } else {
            month.toString()
        }
        return String.format("%s/%s/%d", dayString, monthString, year)
    }

    fun hideKeyboardFrom(context: Context, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun formatCalendars(date: Long) : String {
        val simpleFormatter = SimpleDateFormat("dd/MM/yyyy")
        return simpleFormatter.format(date).toString()
    }

}