package com.jason.app.extensions

import android.util.Log


fun String.logError(message: String) {
    Log.e(this, message)
}