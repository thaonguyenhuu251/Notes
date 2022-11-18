package com.example.notes.util

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import com.example.notes.util.PreferencesSettings

object PreferencesSettings {
    private const val PREF_FILE = "settings_pref"

    fun saveToFinger(context: Context, boolean: Boolean) {
        val sharedPref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean(Constants.FINGERON, boolean)
        editor.apply()
    }

    fun getFinger(context: Context): Boolean {
        val sharedPref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val defaultValue = false
        return sharedPref.getBoolean(Constants.FINGERON, defaultValue)
    }

    fun saveToPref(context: Context, str: String?) {
        val sharedPref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(Constants.PASSCODE, str)
        editor.apply()
    }

    fun getCode(context: Context): String? {
        val sharedPref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val defaultValue = ""
        return sharedPref.getString(Constants.PASSCODE, defaultValue)
    }

    fun setThemes(context: Context, str: Boolean) {
        val sharedPref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean(Constants.THEMES, str)
        editor.apply()
    }

    fun getThemes(context: Context): Boolean {
        val sharedPref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val defaultValue = false
        return sharedPref.getBoolean(Constants.THEMES, defaultValue)
    }

    fun saveToBackground(context: Context, str: Int) {
        val sharedPref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt(Constants.BACKGROUND, str)
        editor.apply()
    }

    fun getBackground(context: Context): Int {
        val sharedPref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val defaultValue = Constants.theme
        return sharedPref.getInt(Constants.BACKGROUND, defaultValue)
    }

    fun saveToColor(context: Context, str: Int) {
        val sharedPref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt(Constants.COLOR, str)
        editor.apply()
    }

    fun getColor(context: Context): Int {
        val sharedPref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val defaultValue = Constants.colorApp
        return sharedPref.getInt(Constants.COLOR, defaultValue)
    }

    fun setLanguage(context: Context, str: String) {
        val sharedPref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(Constants.LANGUAGE, str)
        editor.apply()
    }

    fun getLanguage(context: Context): String? {
        val sharedPref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val defaultValue = Constants.LG_VIETNAM
        return sharedPref.getString(Constants.LANGUAGE, defaultValue)
    }

    fun setTheme(context: Context, str: Int) {
        val sharedPref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt(Constants.THEME, str)
        editor.apply()
    }

    fun getTheme(context: Context): Int {
        val sharedPref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val defaultValue = Constants.themes
        return sharedPref.getInt(Constants.THEME, defaultValue)
    }

    fun setLayout(context: Context, str: Int) {
        val sharedPref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt(Constants.THEME, str)
        editor.apply()
    }

    fun getLayout(context: Context): Int {
        val sharedPref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val defaultValue = Constants.themes
        return sharedPref.getInt(Constants.THEME, defaultValue)
    }

    fun applyTheme(theme: Int) {
        when (theme) {
            AppCompatDelegate.MODE_NIGHT_NO, AppCompatDelegate.MODE_NIGHT_YES -> {
                AppCompatDelegate.setDefaultNightMode(theme)
            }
            else -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
                }
            }
        }
    }

    fun setViewMode(context: Context, str: Int) {
        val sharedPref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt(Constants.VIEW_MODE, str)
        editor.apply()
    }

    fun getViewMode(context: Context): Int {
        val sharedPref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val defaultValue = Constants.viewMode
        return sharedPref.getInt(Constants.VIEW_MODE, defaultValue)
    }
}