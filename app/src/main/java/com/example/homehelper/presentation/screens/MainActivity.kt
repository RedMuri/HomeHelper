package com.example.homehelper.presentation.screens

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.homehelper.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        checkUserSigned()
    }

    private fun checkUserSigned() {
        auth = Firebase.auth
        val currentUser = auth.currentUser
        Log.i("muri", currentUser.toString())
        if (currentUser == null)
            startActivity(AuthActivity.newIntent(application))
    }
}