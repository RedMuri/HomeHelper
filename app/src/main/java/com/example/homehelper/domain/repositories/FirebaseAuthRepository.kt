package com.example.homehelper.domain.repositories

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

interface FirebaseAuthRepository {

    fun getFirebaseAuth(): FirebaseAuth
    fun signIn(email: String, password: String, flatNum: Int): Task<AuthResult>
    fun logIn(email: String, password: String): Task<AuthResult>
}