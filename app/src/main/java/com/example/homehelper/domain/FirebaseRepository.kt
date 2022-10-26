package com.example.homehelper.domain

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth

interface FirebaseRepository {

    fun getFirebaseAuth(): FirebaseAuth
    fun getEventsList(): LiveData<List<Event>>
    fun addEvent(title: String,desc: String, date: Long)
}