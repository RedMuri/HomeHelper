package com.example.homehelper.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.homehelper.domain.usecases.chats.GetChatsUseCase
import com.example.homehelper.domain.usecases.chats.LoadChatsFromFbUseCase
import com.example.homehelper.domain.usecases.chats.StartChatWithSomeoneUseCase
import com.example.homehelper.presentation.HomeHelperApp
import javax.inject.Inject

class ChatsListViewModel @Inject constructor(
    private val getChatsUseCase: GetChatsUseCase,
    private val startChatWithSomeoneUseCase: StartChatWithSomeoneUseCase,
    loadChatsFromFbUseCase: LoadChatsFromFbUseCase,
    application: Application,
) : ViewModel() {

    init {
        val email = (application as HomeHelperApp).sharedPreferences.getString(
            HomeHelperApp.USER_EMAIL, "none")
        if (email != null) {
            loadChatsFromFbUseCase.invoke(email)
        }
    }


    fun getChats(userEmail: String) = getChatsUseCase.invoke(userEmail)

    fun startChatWithSomeone(userEmail: String, someoneEmail: String) {
        startChatWithSomeoneUseCase.invoke(userEmail, someoneEmail)
    }
}