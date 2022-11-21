package com.example.homehelper.domain.repositories

import androidx.lifecycle.LiveData
import com.example.homehelper.domain.entities.Chat

interface FirebaseChatsRepository {

    fun getChats(userEmail: String): LiveData<List<Chat>>
    fun startChatWithSomeone(userEmail: String, someoneEmail: String, callback: (String) -> Unit)
    fun loadUserChatsFromFb(userEmail: String)
}