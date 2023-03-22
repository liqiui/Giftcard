package com.example.giftcard.utils

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    var username: String?
        get() = sharedPreferences.getString("username", null)
        set(value) = sharedPreferences.edit().putString("username", value).apply()

    var biometricEnabled: Boolean
        get() = sharedPreferences.getBoolean("biometric_enabled", false)
        set(value) = sharedPreferences.edit().putBoolean("biometric_enabled", value).apply()

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}
