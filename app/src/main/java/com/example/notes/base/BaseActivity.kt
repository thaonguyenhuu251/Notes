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
            Constants.LG_VIETNAM -> {
                "vi"
            }
            Constants.LG_ENGLISH -> {
                "en"
            }
            Constants.LG_RUSSIA -> {
                "ru"
            }
            Constants.LG_LAO -> {
                "la"
            }
            Constants.LG_THAILAND -> {
                "th"
            }
            Constants.LG_MYANMAR -> {
                "my"
            }
            Constants.LG_KOREAN -> {
                "ko"
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