package com.whiz.reto.core.extensions

import android.content.res.Resources

fun Int?.notNull(): Int {
    return this ?: 0
}

val Int.dpToPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Int.addLeadingZero(): String {
    return if (this < 10) {
        "0$this"
    } else {
        this.toString()
    }
}