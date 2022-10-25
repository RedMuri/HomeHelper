package com.example.homehelper.domain

import com.google.firebase.auth.FirebaseAuth

interface FirebaseRepository {

    fun getFirebaseAuth(): FirebaseAuth
    fun getEventsList(): List<Event>
    fun addEvent(event: Event)
}