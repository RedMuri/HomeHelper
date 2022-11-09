package com.example.homehelper.domain.usecases.chats

import com.example.homehelper.domain.repositories.FirebaseChatsRepository
import javax.inject.Inject

class GetChatsUseCase @Inject constructor(
    private val repository: FirebaseChatsRepository
) {

    operator fun invoke(userEmail: String) = repository.getChats(userEmail)
}