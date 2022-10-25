package com.example.homehelper.data.firebase

import com.example.homehelper.domain.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
): FirebaseRepository{

    override fun getFirebaseAuth(): FirebaseAuth = auth
}