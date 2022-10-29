package com.example.homehelper.presentation

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.homehelper.di.DaggerApplicationComponent

class HomeHelperApp: Application() {

    val component by lazy {
        DaggerApplicationComponent
            .factory()
            .create(this)
    }

    val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
    }

    companion object{

        private const val SETTINGS = "settings"
        const val USER_EMAIL = "user_name"
        const val USER_FLAT_NUM = "user_flat_num"
        const val ADMIN_USER_NAME = "admin@mail.ru"
    }
}