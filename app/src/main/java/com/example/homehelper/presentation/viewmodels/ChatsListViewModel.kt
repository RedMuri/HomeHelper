package com.example.homehelper.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.homehelper.domain.entities.Chat
import com.example.homehelper.domain.entities.User
import com.example.homehelper.domain.usecases.GetChatsUseCase
import com.example.homehelper.domain.usecases.GetCurrentUserUseCase
import com.google.firebase.firestore.ktx.toObject
import javax.inject.Inject

class ChatsListViewModel @Inject constructor(
    private val getChatsUseCase: GetChatsUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
) : ViewModel() {


    fun getCurrentUser(email: String) = getCurrentUserUseCase(email)

    fun getChats(userChats: List<String>) = getChatsUseCase.invoke(userChats)

}