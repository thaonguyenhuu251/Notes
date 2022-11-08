package com.example.notes.util

import android.os.Bundle

var Bundle.date
    get() = getLong("date")
    set(value) = putLong("date", value)

var Bundle.nameMedicine
    get() = getString("name_medicine")
    set(value) = putString("name_medicine", value)

var Bundle.idMedicine
    get() = getLong("id_medicine")
    set(value) = putLong("id_medicine", value)

var Bundle.search
    get() = getString("search")
    set(value) = putString("search", value)