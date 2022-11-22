package com.example.homehelper.domain.usecases.chats

import com.example.homehelper.domain.repositories.FirebaseChatsRepository
import javax.inject.Inject

class StartChatWithSomeoneUseCase @Inject constructor(
    private val repository: FirebaseChatsRepository,
) {

    operator fun invoke(userEmail: String, someoneEmail: String, callback: (String) -> Unit) =
        repository.startChatWithSomeone(userEmail, someoneEmail, callback)
}