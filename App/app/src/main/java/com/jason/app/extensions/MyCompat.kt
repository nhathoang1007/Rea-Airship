package com.jason.app.extensions

import android.content.Context
import androidx.core.content.ContextCompat
import com.jason.app.MyApp

fun applicationContext(): Context {
    return MyApp.instance.applicationContext
}

fun Int.getColor(): Int {
    return ContextCompat.getColor(applicationContext(), this)
}


fun Int.getString(): String {
    return applicationContext().getString(this)
}
