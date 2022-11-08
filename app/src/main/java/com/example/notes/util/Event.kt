package com.example.notes.util

import com.example.notes.App

object Event {
    const val EVENT_SEARCH_DOCUMENT = "SEARCH_DOCUMENT"

    const val EVENT_SORT_NAME_AC = "EVENT_SORT_NAME_AC"
    const val EVENT_SORT_TIME_CREATE = "EVENT_SORT_TIME_CREATE"
    const val EVENT_SORT_TIME_OPEN = "EVENT_SORT_TIME_OPEN"
    const val EVENT_SORT_TIME_AC = "EVENT_SORT_NAME_AC"
    const val EVENT_SORT_TIME_DC = "EVENT_SORT_TIME_DC"

    const val EVENT_CHANGE_BACKGROUND = "EVENT_CHANGE_BACKGROUND"
    const val EVENT_CHANGE_LANGUAGE = "EVENT_CHANGE_BACKGROUND"

    fun eventSortNameAscending() {
        App.eventBus.onNext(hashMapOf(EVENT_SORT_NAME_AC to Constants.SORT_AC))
    }

    fun eventSortTimeCreate() {
        App.eventBus.onNext(hashMapOf(EVENT_SORT_TIME_CREATE to Constants.SORT_DC))
    }

    fun eventSortTimeOpen() {
        App.eventBus.onNext(hashMapOf(EVENT_SORT_TIME_OPEN to Constants.SORT_DC))
    }

    fun eventSortTimeAscending() {
        App.eventBus.onNext(hashMapOf(EVENT_SORT_TIME_AC to Constants.SORT_AC))
    }

    fun eventSortTimeDecrease() {
        App.eventBus.onNext(hashMapOf(EVENT_SORT_TIME_DC to Constants.SORT_DC))
    }

    fun searchDocument(searchText: String) {
        App.eventBus.onNext(hashMapOf(EVENT_SEARCH_DOCUMENT to searchText))
    }

    fun eventChangeBackground(background: Int) {
        App.eventBus.onNext(hashMapOf(EVENT_CHANGE_BACKGROUND to background))
    }

    fun eventChangeLanguage() {
        App.eventBus.onNext(hashMapOf(EVENT_CHANGE_LANGUAGE to Constants.LANGUAGE))
    }
}