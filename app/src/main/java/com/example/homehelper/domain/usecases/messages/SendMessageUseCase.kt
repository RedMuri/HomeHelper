package com.example.homehelper.domain.usecases.messages

import com.example.homehelper.domain.repositories.FirebaseMessagesRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val repository: FirebaseMessagesRepository
) {

    operator fun invoke(text: String, author: String,chatName: String) = repository.sendMessage(text, author,chatName)
}