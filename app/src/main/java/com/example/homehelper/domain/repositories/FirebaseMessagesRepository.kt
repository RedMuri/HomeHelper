package com.example.homehelper.domain.repositories

import androidx.lifecycle.LiveData
import com.example.homehelper.domain.entities.Message

interface FirebaseMessagesRepository {

    fun sendMessage(text: String, author: String,chatName: String)
    fun getMessages(chatName: String): LiveData<List<Message>>
}