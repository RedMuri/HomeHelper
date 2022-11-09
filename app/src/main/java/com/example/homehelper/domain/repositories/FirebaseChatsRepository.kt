package com.example.homehelper.domain.repositories

import androidx.lifecycle.MutableLiveData
import com.example.homehelper.domain.entities.Chat

interface FirebaseChatsRepository {

    fun getChats(userEmail: String): MutableLiveData<MutableList<Chat>>
    fun startChatWithSomeone(userEmail: String, someoneEmail: String)
}