package com.example.homehelper.presentation

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.homehelper.di.DaggerApplicationComponent
import com.example.homehelper.presentation.screens.auth.AuthActivity

class HomeHelperApp : Application() {

    val component by lazy {
        DaggerApplicationComponent
            .factory()
            .create(this)
    }

    val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
    }

    fun putUserEmail(userEmail: String) {
        sharedPreferences.edit()
            .putString(USER_EMAIL, userEmail).apply()
    }

    fun getUserEmail(): String {
        return sharedPreferences.getString(USER_EMAIL, DEF_VALUE_USER_EMAIL) ?: DEF_VALUE_USER_EMAIL
    }

    companion object {

        private const val SETTINGS = "settings"
        const val USER_EMAIL = "user_name"
        const val USER_FLAT_NUM = "user_flat_num"
        const val ADMIN_USER_NAME = "admin@mail.ru"
        const val DEF_VALUE_USER_EMAIL = "none"
    }
}