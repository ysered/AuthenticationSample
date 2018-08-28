package com.ysered.authenticationsample.ext

import android.support.v4.content.ContextCompat
import android.view.View


fun View.getColor(color: Int): Int {
    return ContextCompat.getColor(context, color)
}
