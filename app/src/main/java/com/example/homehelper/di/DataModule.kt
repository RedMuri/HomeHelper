package com.example.homehelper.di

import android.app.Application
import com.example.homehelper.presentation.HomeHelperApp
import dagger.Module
import dagger.Provides


@Module
interface DataModule {

    companion object{

        @UserNameQualifier
        @Provides
        fun provideUserName(application: Application): String {
            return (application as HomeHelperApp).sharedPreferences
                .getString(HomeHelperApp.USER_NAME, "none")
                ?: "null"
        }
    }
}