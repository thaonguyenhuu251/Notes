package com.example.notes

import android.app.Application
import com.example.notes.base.BaseActivity
import com.example.notes.util.Constants
import com.example.notes.util.Event
import com.example.notes.util.PreferencesSettings
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.*

class App : Application() {
    companion object {
        val eventBus: PublishSubject<HashMap<String, Any>> by lazy { PublishSubject.create() }
    }
    private var disposable: Disposable? = null
    override fun onCreate() {
        super.onCreate()
        var change = ""
        val language = PreferencesSettings.getLanguage(this)
        change = when (language) {
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

        BaseActivity.dLocale = Locale(change)

        disposable = App.eventBus.subscribe{
            it[Event.EVENT_CHANGE_BACKGROUND]?.let { data->
                (data as Int?)?.let { background ->
                    setTheme(background)
                }
            }
        }
    }
}