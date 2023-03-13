package com.example.giftcard.utils

import android.content.Context

class UserManager(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    var username: String?
        get() = sharedPreferences.getString("username", null)
        set(value) = sharedPreferences.edit().putString("username", value).apply()

    var password: String?
        get() = sharedPreferences.getString("password", null)
        set(value) = sharedPreferences.edit().putString("password", value).apply()

    var biometricEnabled: Boolean
        get() = sharedPreferences.getBoolean("biometric_enabled", false)
        set(value) = sharedPreferences.edit().putBoolean("biometric_enabled", value).apply()

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}