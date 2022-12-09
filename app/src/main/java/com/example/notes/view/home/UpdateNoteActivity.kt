package com.example.notes.view.home

import android.os.Bundle
import com.example.notes.R
import com.example.notes.base.BaseActivity
import com.example.notes.util.PreferencesSettings

class UpdateNoteActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setTheme(PreferencesSettings.getBackground(this))
        setContentView(R.layout.activity_update_note)
    }
}