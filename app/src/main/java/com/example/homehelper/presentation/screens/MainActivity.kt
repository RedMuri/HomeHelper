package com.example.homehelper.presentation.screens

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.homehelper.R
import com.example.homehelper.presentation.HomeHelperApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val component by lazy {
        (application as HomeHelperApp).component
    }

    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        checkUserSigned()
    }

    private fun checkUserSigned() {
        val currentUser = auth.currentUser
        Log.i("muri", currentUser.toString())
        if (currentUser == null)
            startActivity(AuthActivity.newIntent(application))
    }
}