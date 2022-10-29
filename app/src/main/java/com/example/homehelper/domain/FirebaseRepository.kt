package com.example.homehelper.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.homehelper.domain.entities.Chat
import com.example.homehelper.domain.entities.Event
import com.example.homehelper.domain.entities.Message
import com.example.homehelper.domain.entities.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

interface FirebaseRepository {

    fun getFirebaseAuth(): FirebaseAuth
    fun getEventsList(): LiveData<List<Event>>
    fun addEvent(title: String,desc: String, date: Long)
    fun deleteEvent(eventId: String)
    fun sendMessage(text: String, author: String,chatName: String)
    fun getMessages(chatName: String): LiveData<List<Message>>
    fun signIn(email: String, password: String, flatNum: Int): Task<AuthResult>
    fun logIn(email: String, password: String): Task<AuthResult>
    fun getChats(userEmail: String): MutableLiveData<MutableList<Chat>>
    fun getCurrentUser(email: String): LiveData<User>
    fun startChatWithSomeone(userEmail: String, someoneEmail: String)
}