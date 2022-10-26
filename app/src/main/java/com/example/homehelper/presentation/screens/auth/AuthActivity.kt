package com.example.homehelper.presentation.screens.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.homehelper.R
import com.example.homehelper.presentation.HomeHelperApp

class AuthActivity : AppCompatActivity() {

    val settings by lazy {
        (application as HomeHelperApp).sharedPreferences
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }

    companion object{

        fun newIntent(context: Context) = Intent(context, AuthActivity::class.java)
    }
}