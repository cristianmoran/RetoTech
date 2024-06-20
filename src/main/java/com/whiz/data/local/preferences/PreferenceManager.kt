package com.whiz.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class PreferenceManager(private val context: Context) {

    private val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val prefSession by lazy {
        EncryptedSharedPreferences.create(
            "encrypted_preferences",
            masterKey,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }


    private var editorSession: SharedPreferences.Editor = prefSession.edit()

    fun saveDataUser(dataUser: String, token: String) {
        editorSession.putString(CONST_KEY_DATA_USER, dataUser)
        editorSession.putString(CONST_KEY_TOKEN, token)
        editorSession.commit()
    }

    fun savePositionTrack(orderId: String, position: Int) {
        editorSession.putInt(orderId, position)
        editorSession.commit()
    }

    fun getPositionTrack(orderId: String): Int {
        return prefSession.getInt(orderId, 0)
    }

    fun updateIntroIsFinish() {
        editorSession.putBoolean(CONST_KEY_INTRO, true)
        editorSession.commit()
    }

    fun getIntroIsFinish(): Boolean {
        return prefSession.getBoolean(CONST_KEY_INTRO, false)
    }


    fun saveDataOrder(dataOrder: String) {
        editorSession.putString(CONST_KEY_DATA_ORDER, dataOrder)
        editorSession.commit()
    }

    fun setUserIsLogin() {
        editorSession.putBoolean(CONST_KEY_IS_LOGIN, true)
        editorSession.commit()
    }

    fun validateIsLogin(): Boolean {
        return prefSession.getBoolean(CONST_KEY_IS_LOGIN, false)
    }

    fun getDataUser(): String {
        return prefSession.getString(CONST_KEY_DATA_USER, String()).toString()
    }

    fun getDataOrderInProgress(): String {
        return prefSession.getString(CONST_KEY_DATA_ORDER, String()).toString()
    }

    fun getDataToken(): String {
        return prefSession.getString(CONST_KEY_TOKEN, String()).toString()
    }

    fun getDateSelected(): String {
        return prefSession.getString(CONST_KEY_DATE_SELECTED, String()).toString()
    }

    fun updateDateSelected(newDate: String) {
        editorSession.putString(CONST_KEY_DATE_SELECTED, newDate)
        editorSession.commit()
    }

    fun clearDataPreference() {
        editorSession.clear().commit()
    }

    companion object {
        const val CONST_PREFERENCE_SESSION = "PREFERENCE_SESSION"

        const val CONST_KEY_IS_LOGIN = "KEY_IS_LOGIN"
        const val CONST_KEY_DATA_USER = "KEY_DATA_USER"
        const val CONST_KEY_SERVICE_IS_ALIVE = "KEY_SERVICE_IS_ALIVE"
        const val CONST_KEY_DATA_ORDER = "KEY_DATA_ORDER"
        const val CONST_KEY_TOKEN = "KEY_DATA_TOKEN"
        const val CONST_KEY_DATE_SELECTED = "KEY_DATE_SELECTED"
        const val CONST_KEY_INTRO = "CONST_KEY_INTRO"

    }

}