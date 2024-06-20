package com.whiz.reto.core.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.provider.Settings

@SuppressLint("NewApi")
fun isAirplaneModeActive(context: Context): Boolean {
    return Settings.Global.getInt(context.contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0
}

@SuppressLint("NewApi", "MissingPermission")
fun isConnectedRed(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return cm.getNetworkCapabilities(cm.activeNetwork)
        ?.hasCapability((NetworkCapabilities.NET_CAPABILITY_INTERNET)) ?: false
}

fun Context.isConnected(): Boolean {
    return isConnectedRed(this) && !isAirplaneModeActive(this)
}
