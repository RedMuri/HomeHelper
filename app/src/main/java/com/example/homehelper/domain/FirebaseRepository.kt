package com.example.homehelper.domain

import androidx.lifecycle.LiveData
import com.example.homehelper.domain.entities.Event
import com.example.homehelper.domain.entities.Message
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Deferred

interface FirebaseRepository {

    fun getFirebaseAuth(): FirebaseAuth
    fun getEventsList(): LiveData<List<Event>>
    fun addEvent(title: String,desc: String, date: Long)
    fun deleteEvent(eventId: String)
    fun sendMessage(text: String, author: String)
    fun getMessages(): LiveData<List<Message>>
    fun signIn(email: String, password: String, flatNum: Int): Task<AuthResult>
    fun logIn(email: String, password: String): Task<AuthResult>
}