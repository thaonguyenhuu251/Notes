package com.example.notes.util

import android.content.Context
import android.content.SharedPreferences
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

    fun setPassword(context: Context, str: Boolean) {
        val sharedPref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean(Constants.PASSWORD, str)
        editor.apply()
    }

    fun getPassword(context: Context): Boolean {
        val sharedPref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val defaultValue = false
        return sharedPref.getBoolean(Constants.PASSWORD, defaultValue)
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

    fun setLanguage(context: Context, str: String) {
        val sharedPref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(Constants.LANGUAGE, str)
        editor.apply()
    }

    fun getLanguage(context: Context): String? {
        val sharedPref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val defaultValue = ""
        return sharedPref.getString(Constants.LANGUAGE, defaultValue)
    }
}