package com.example.homehelper.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.homehelper.domain.entities.Message
import com.example.homehelper.domain.usecases.messages.GetMessagesUseCase
import com.example.homehelper.domain.usecases.messages.SendMessageUseCase
import javax.inject.Inject

class ChatViewModel @Inject constructor(
    private val sendMessageUseCase: SendMessageUseCase,
    private val getMessagesUseCase: GetMessagesUseCase
) : ViewModel() {


    fun sendMessage(text: String, author: String, chatId: String) {
        sendMessageUseCase.invoke(text, author, chatId)
    }

    fun getMessages(chatId: String): LiveData<List<Message>>{
        return getMessagesUseCase.invoke(chatId)
    }
}