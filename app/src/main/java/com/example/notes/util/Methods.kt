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
            Constants.LG_VIETNAM -> "VietNam"
            Constants.LG_ENGLISH -> "English"
            Constants.LG_RUSSIA -> "Russia"
            Constants.LG_KOREAN -> "Korean"
            Constants.LG_LAO -> "Lao"
            Constants.LG_THAILAND -> "Thai lan"
            Constants.LG_MYANMAR -> "Myanmar"
            else -> "Viet Nam"
        }
    }

    fun getLanguages(language: String): Int {
        return when (language) {
            Constants.LG_VIETNAM -> R.drawable.ic_language_vietnam
            Constants.LG_ENGLISH -> R.drawable.ic_language_english_uk
            Constants.LG_RUSSIA -> R.drawable.ic_language_russian
            Constants.LG_KOREAN -> R.drawable.ic_language_south_korea
            Constants.LG_LAO -> R.drawable.ic_language_south_korea
            Constants.LG_THAILAND -> R.drawable.ic_language_south_korea
            Constants.LG_MYANMAR -> R.drawable.ic_language_south_korea
            else -> R.drawable.ic_language_vietnam
        }
    }
}