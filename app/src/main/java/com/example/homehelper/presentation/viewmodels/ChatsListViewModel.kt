package com.example.homehelper.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.homehelper.domain.usecases.GetChatsUseCase
import javax.inject.Inject

class ChatsListViewModel @Inject constructor(
    private val getChatsUseCase: GetChatsUseCase
) : ViewModel() {

    fun getChats(flatNum: Int) = getChatsUseCase.invoke(flatNum)
}