package com.example.homehelper.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.homehelper.domain.usecases.GetChatsUseCase
import com.example.homehelper.domain.usecases.GetCurrentUserUseCase
import com.example.homehelper.domain.usecases.StartChatWIthSomeoneUseCase
import javax.inject.Inject

class ChatsListViewModel @Inject constructor(
    private val getChatsUseCase: GetChatsUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val startChatWIthSomeoneUseCase: StartChatWIthSomeoneUseCase,
) : ViewModel() {


    fun getCurrentUser(email: String) = getCurrentUserUseCase(email)

    fun getChats(userEmail: String) = getChatsUseCase.invoke(userEmail)

    fun startChatWithSomeone(userEmail: String, someoneEmail: String) {
        startChatWIthSomeoneUseCase.invoke(userEmail, someoneEmail)
    }
}