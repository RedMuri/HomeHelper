package com.example.homehelper.domain.repositories

import androidx.lifecycle.LiveData
import com.example.homehelper.domain.entities.Message

interface FirebaseMessagesRepository {

    fun sendMessage(text: String, author: String,chatId: String)
    fun getMessages(chatId: String): LiveData<List<Message>>
}