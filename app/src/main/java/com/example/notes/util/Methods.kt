package com.example.notes.util

import com.example.notes.R

object Methods {
    fun getColorTheme(string: String): Int {
        return when (string) {
            Constants.COLOR_RED -> R.style.AppTheme_red
            Constants.COLOR_PINK -> R.style.AppTheme_pink
            Constants.COLOR_DARKPINK -> R.style.AppTheme_darkpink
            Constants.COLOR_VIOLET -> R.style.AppTheme_violet
            Constants.COLOR_BLUE -> R.style.AppTheme_blue
            Constants.COLOR_SKYBLUE -> R.style.AppTheme_skyblue
            Constants.COLOR_GREEN -> R.style.AppTheme_green
            Constants.COLOR_GREY -> R.style.AppTheme_grey
            Constants.COLOR_BROWN -> R.style.AppTheme_brown
            else -> R.style.AppTheme
        }
    }
}