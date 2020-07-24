package com.molde.molde.util

import android.content.Context.MODE_PRIVATE
import com.molde.molde.AppComponent

class SharedPreferencesManager {

    companion object {
        const val SHARED_PREFERENCES = "molde_sp"
        const val TOKEN = "molde_token"
    }

    private val sharedPreferences = AppComponent.context?.getSharedPreferences(
        SHARED_PREFERENCES,
        MODE_PRIVATE
    )
    private val sharedPreferencesEditor = sharedPreferences?.edit()

    fun putToken(token: String) {
        sharedPreferencesEditor?.putString(TOKEN, token)?.apply()
    }

    fun getToken(): String {
        return sharedPreferences?.getString(TOKEN, "") ?: ""
    }

    fun clearToken() {
        sharedPreferencesEditor?.remove(TOKEN)?.apply()
    }

}