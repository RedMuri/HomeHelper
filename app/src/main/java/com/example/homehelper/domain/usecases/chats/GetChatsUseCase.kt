package com.example.homehelper.domain.usecases.chats

import com.example.homehelper.domain.FirebaseRepository
import javax.inject.Inject

class GetChatsUseCase @Inject constructor(
    private val repository: FirebaseRepository
) {

    operator fun invoke(userEmail: String) = repository.getChats(userEmail)
}