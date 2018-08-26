package com.ysered.authenticationsample.ext

import android.util.Log

fun Any.debug(message: String, throwable: Throwable? = null) {
    Log.d(this::class.java.simpleName, message, throwable)
}
