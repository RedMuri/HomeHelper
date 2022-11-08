package com.example.homehelper.domain.usecases.chats

import com.example.homehelper.domain.FirebaseRepository
import javax.inject.Inject

class StartChatWithSomeoneUseCase @Inject constructor(
    private val repository: FirebaseRepository
) {

    operator fun invoke(userEmail: String, someoneEmail: String) = repository.startChatWithSomeone(userEmail, someoneEmail)
}