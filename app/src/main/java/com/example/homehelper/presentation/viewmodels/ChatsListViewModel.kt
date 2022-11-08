package com.example.homehelper.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.homehelper.domain.usecases.chats.GetChatsUseCase
import com.example.homehelper.domain.usecases.chats.StartChatWithSomeoneUseCase
import javax.inject.Inject

class ChatsListViewModel @Inject constructor(
    private val getChatsUseCase: GetChatsUseCase,
    private val startChatWithSomeoneUseCase: StartChatWithSomeoneUseCase,
) : ViewModel() {


    fun getChats(userEmail: String) = getChatsUseCase.invoke(userEmail)

    fun startChatWithSomeone(userEmail: String, someoneEmail: String) {
        startChatWithSomeoneUseCase.invoke(userEmail, someoneEmail)
    }
}