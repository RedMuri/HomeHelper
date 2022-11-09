package com.example.homehelper.domain

import androidx.lifecycle.MutableLiveData
import com.example.homehelper.domain.entities.Chat
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

interface FirebaseRepository {

    fun getChats(userEmail: String): MutableLiveData<MutableList<Chat>>
    fun startChatWithSomeone(userEmail: String, someoneEmail: String)
}