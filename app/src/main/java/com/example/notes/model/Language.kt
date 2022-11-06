package com.example.notes.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

class Language {
    var textLanguage:Int = 0
    var drawableLanguage:Int = 0

    constructor(textLanguage: Int, drawableLanguage: Int) {
        this.textLanguage = textLanguage
        this.drawableLanguage = drawableLanguage
    }
}