package com.example.notes.util

import com.example.notes.R

class Methods {
    fun setColorTheme() {
        when (Constants.color) {
            -0xbbcca -> Constants.theme = R.style.AppTheme_red
            -0x16e19d -> Constants.theme = R.style.AppTheme_pink
            -0x63d850 -> Constants.theme = R.style.AppTheme_darpink
            -0x98c549 -> Constants.theme = R.style.AppTheme_violet
            -0xc0ae4b -> Constants.theme = R.style.AppTheme_blue
            -0xfc560c -> Constants.theme = R.style.AppTheme_skyblue
            -0xb350b0 -> Constants.theme = R.style.AppTheme_green
            -0x6800 -> Constants.theme = R.style.AppTheme
            -0x616162 -> Constants.theme = R.style.AppTheme_grey
            -0x86aab8 -> Constants.theme = R.style.AppTheme_brown
            else -> Constants.theme = R.style.AppTheme
        }
    }
}