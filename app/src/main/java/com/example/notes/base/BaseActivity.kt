package com.example.notes.base

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.notes.App
import com.example.notes.util.Constants
import com.example.notes.util.ContextUtils
import com.example.notes.util.Event
import com.example.notes.util.PreferencesSettings
import io.reactivex.rxjava3.disposables.Disposable
import java.util.*

open class BaseActivity : AppCompatActivity() {

    companion object {
        var dLocale: Locale? = null

    }
    private var disposable: Disposable? = null
    var localeUpdatedContext: ContextWrapper? = null

    override fun onConfigurationChanged(newConfig: Configuration) {
        newConfig.setLocale(dLocale)
        newConfig.setLayoutDirection(dLocale)
        super.onConfigurationChanged(newConfig)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        disposable = App.eventBus.subscribe{
            it[Event.EVENT_CHANGE_BACKGROUND]?.let {
                onRestart()
            }

            it[Event.EVENT_CHANGE_LANGUAGE]?.let {
                onRestart()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        disposable = App.eventBus.subscribe{
            it[Event.EVENT_CHANGE_BACKGROUND]?.let {

            }

            it[Event.EVENT_CHANGE_LANGUAGE]?.let {

            }
        }

    }

    override fun attachBaseContext(newBase: Context) {
        val change = when (PreferencesSettings.getLanguage(newBase)) {
            Constants.LG_VIETNAMESE -> {
                "vi"
            }
            Constants.LG_ENGLISH -> {
                "en"
            }
            Constants.LG_RUSSIAN -> {
                "sah"
            }
            Constants.LG_LAOS -> {
                "lo"
            }
            Constants.LG_THAI -> {
                "th"
            }
            Constants.LG_KOREAN -> {
                "ko"
            }
            Constants.LG_CHINESE -> {
                "zh"
            }
            Constants.LG_JAPANESE -> {
                "ja"
            }
            Constants.LG_INDONESIAN -> {
                "in"
            }
            Constants.LG_SPANISH -> {
                "es"
            }
            Constants.LG_FRENCH -> {
                "fr"
            }
            Constants.LG_INDIAN -> {
                "kn"
            }
            Constants.LG_GERMAN -> {
                "de"
            }
            Constants.LG_ITALIAN -> {
                "it"
            }
            else -> {
                "en"
            }
        }
        dLocale = Locale(change)
        localeUpdatedContext = ContextUtils.updateLocale(newBase, dLocale!!)
        super.attachBaseContext(localeUpdatedContext)
    }
}