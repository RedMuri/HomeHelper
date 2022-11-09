package com.example.homehelper.domain.usecases.messages

import com.example.homehelper.domain.repositories.FirebaseMessagesRepository
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(
    private val repository: FirebaseMessagesRepository
) {

    operator fun invoke(chatName: String) = repository.getMessages(chatName)
}