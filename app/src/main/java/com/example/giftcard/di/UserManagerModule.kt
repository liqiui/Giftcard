package com.example.giftcard.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.giftcard.utils.UserManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    // Add this to provide the UserManager dependency
    @Provides
    @Singleton
    fun provideUserManager(sharedPreferences: SharedPreferences): UserManager {
        return UserManager(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(appContext)
    }
}
