package com.example.homehelper.domain

import androidx.lifecycle.LiveData
import com.example.homehelper.domain.entities.Event
import com.example.homehelper.domain.entities.Message
import com.google.firebase.auth.FirebaseAuth

interface FirebaseRepository {

    fun getFirebaseAuth(): FirebaseAuth
    fun getEventsList(): LiveData<List<Event>>
    fun addEvent(title: String,desc: String, date: Long)
    fun deleteEvent(eventId: String)
    fun sendMessage(text: String, author: String)
    fun getMessages(): LiveData<List<Message>>
}