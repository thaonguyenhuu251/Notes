package com.example.notes.util

import com.example.notes.R

object Methods {
    fun getColorTheme(color: String): Int {
        return when (color) {
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

    fun getColorDark(color: String): Int {
        return when (color) {
            Constants.COLOR_RED -> R.color.red_dark
            Constants.COLOR_PINK -> R.color.pink_dark
            Constants.COLOR_DARKPINK -> R.color.pink_dark
            Constants.COLOR_VIOLET -> R.color.violet_colorPrimaryDark
            Constants.COLOR_BLUE -> R.color.blue_primary
            Constants.COLOR_SKYBLUE -> R.color.skyblue_colorPrimary
            Constants.COLOR_GREEN -> R.color.green_colorPrimary
            Constants.COLOR_GREY -> R.color.grey_colorPrimaryDark
            Constants.COLOR_BROWN -> R.color.brown_colorPrimary
            else -> R.color.blue_primary
        }
    }

    fun getColorLight(color: String): Int {
        return when (color) {
            Constants.COLOR_RED -> R.color.red_light
            Constants.COLOR_PINK -> R.color.pink_light
            Constants.COLOR_DARKPINK -> R.color.light_pink
            Constants.COLOR_VIOLET -> R.color.violet_light
            Constants.COLOR_BLUE -> R.color.blue_light
            Constants.COLOR_SKYBLUE -> R.color.light_sky_blue
            Constants.COLOR_GREEN -> R.color.green_light
            Constants.COLOR_GREY -> R.color.green_light
            Constants.COLOR_BROWN -> R.color.brown_light
            else -> R.color.blue_light
        }
    }

    fun getColorString(color: String): String {
        return when (color) {
            Constants.COLOR_RED -> "Red"
            Constants.COLOR_PINK -> "Pink"
            Constants.COLOR_DARKPINK -> "Dark pink"
            Constants.COLOR_VIOLET -> "Violet"
            Constants.COLOR_BLUE -> "Blue"
            Constants.COLOR_SKYBLUE -> "Sky blue"
            Constants.COLOR_GREEN -> "Green"
            Constants.COLOR_GREY -> "Grey"
            Constants.COLOR_BROWN -> "Brown"
            else -> "Blue"
        }
    }

    fun getStringColor(color: String): String {
        return when (color) {
            "Red" -> Constants.COLOR_RED
            "Pink" -> Constants.COLOR_PINK
            "Dark pink" -> Constants.COLOR_DARKPINK
            "Violet" -> Constants.COLOR_VIOLET
            "Blue" -> Constants.COLOR_BLUE
            "Sky blue" -> Constants.COLOR_SKYBLUE
            "Green" -> Constants.COLOR_GREEN
            "Grey" -> Constants.COLOR_GREY
            "Brown" -> Constants.COLOR_BROWN
            else -> Constants.COLOR_BLUE
        }
    }

    fun getStringLanguage(language: String): String {
        return when (language) {
            Constants.LG_VIETNAMESE -> "Vietnamese"
            Constants.LG_ENGLISH -> "English"
            Constants.LG_RUSSIAN -> "Russian"
            Constants.LG_KOREAN -> "Korean"
            Constants.LG_LAOS -> "Laos"
            Constants.LG_THAI -> "Thai"
            Constants.LG_MYANMAR -> "Myanmar"
            Constants.LG_CHINESE -> "Chinese"
            Constants.LG_JAPANESE -> "Japanese"
            Constants.LG_FILIPINO -> "Filipino"
            Constants.LG_INDONESIAN -> "Indonesian"
            Constants.LG_SPANISH -> "Spanish"
            Constants.LG_FRENCH -> "French"
            Constants.LG_INDIAN -> "Indian"
            Constants.LG_GERMAN -> "German"
            Constants.LG_ITALIAN -> "Italian"
            else -> "Vietnamese"
        }
    }

    fun getLanguages(language: String): Int {
        return when (language) {
            Constants.LG_VIETNAMESE -> R.drawable.ic_language_vietnam
            Constants.LG_ENGLISH -> R.drawable.ic_language_english_uk
            Constants.LG_RUSSIAN -> R.drawable.ic_language_russian
            Constants.LG_KOREAN -> R.drawable.ic_language_south_korea
            Constants.LG_LAOS -> R.drawable.ic_language_laos
            Constants.LG_THAI -> R.drawable.ic_language_thailand
            Constants.LG_MYANMAR -> R.drawable.ic_language_myanmar
            Constants.LG_CHINESE -> R.drawable.ic_language_china
            Constants.LG_JAPANESE -> R.drawable.ic_language_japan
            Constants.LG_FILIPINO -> R.drawable.ic_language_philippines
            Constants.LG_INDONESIAN -> R.drawable.ic_language_indonesia
            Constants.LG_SPANISH -> R.drawable.ic_language_spain
            Constants.LG_FRENCH -> R.drawable.ic_language_france
            Constants.LG_INDIAN -> R.drawable.ic_language_india
            Constants.LG_GERMAN -> R.drawable.ic_language_germany
            Constants.LG_ITALIAN -> R.drawable.ic_language_italy









            else -> R.drawable.ic_language_vietnam
        }
    }
}