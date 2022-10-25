package com.example.homehelper.presentation

import android.app.Application
import com.example.homehelper.di.DaggerApplicationComponent

class HomeHelperApp: Application() {

    val component by lazy {
        DaggerApplicationComponent
            .factory()
            .create(this)
    }
}