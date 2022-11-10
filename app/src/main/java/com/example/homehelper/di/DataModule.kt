package com.example.homehelper.di

import android.app.Application
import com.example.homehelper.data.database.AppDatabase
import com.example.homehelper.data.database.dao.EventsDao
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
                .getString(HomeHelperApp.USER_EMAIL, "none")
                ?: "null"
        }

        @Provides
        fun provideEventsDao(application: Application): EventsDao {
            return AppDatabase.getInstance(application).eventsDao()
        }
    }


}