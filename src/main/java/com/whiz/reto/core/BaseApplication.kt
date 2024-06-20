package com.whiz.reto.core

import android.app.Application
import com.whiz.data.local.preferences.PreferenceManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication : Application() {


    @Inject
    lateinit var preferences: PreferenceManager

}