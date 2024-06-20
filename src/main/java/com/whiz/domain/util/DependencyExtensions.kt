package com.whiz.domain.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.provider.Settings
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("NewApi")
fun Context.isAirplaneModeActive(): Boolean {
    return Settings.Global.getInt(contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0
}

@SuppressLint("NewApi", "MissingPermission")
fun Context.isConnected(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return cm.getNetworkCapabilities(cm.activeNetwork)
        ?.hasCapability((NetworkCapabilities.NET_CAPABILITY_INTERNET)) ?: false
}
