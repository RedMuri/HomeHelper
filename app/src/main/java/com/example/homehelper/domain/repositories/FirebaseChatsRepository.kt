package com.example.homehelper.domain.repositories

import androidx.lifecycle.LiveData
import com.example.homehelper.data.firebase.model.ChatDto
import com.example.homehelper.domain.entities.Chat

interface FirebaseChatsRepository {

    fun getChats(userEmail: String): LiveData<List<Chat>>
    fun startChatWithSomeone(userEmail: String, someoneEmail: String, callback: (ChatDto) -> Unit)
    fun loadUserChatsFromFb(userEmail: String)
}