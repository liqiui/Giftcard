package com.example.giftcard

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GiftCardApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
