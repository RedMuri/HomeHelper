package com.example.homehelper.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.homehelper.domain.entities.Event
import com.example.homehelper.domain.entities.Message
import com.example.homehelper.domain.usecases.GetCurrentUserUseCase
import com.example.homehelper.domain.usecases.GetMessagesUseCase
import com.example.homehelper.domain.usecases.SendMessageUseCase
import javax.inject.Inject

class ChatViewModel @Inject constructor(
    private val sendMessageUseCase: SendMessageUseCase,
    private val getMessagesUseCase: GetMessagesUseCase
) : ViewModel() {


    fun sendMessage(text: String, author: String, chatName: String) {
        sendMessageUseCase.invoke(text, author, chatName)
    }

    fun getMessages(chatName: String): LiveData<List<Message>>{
        return getMessagesUseCase.invoke(chatName)
    }
}