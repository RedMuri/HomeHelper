package com.example.homehelper.domain.usecases.messages

import com.example.homehelper.domain.FirebaseRepository
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(
    private val repository: FirebaseRepository
) {

    operator fun invoke(chatName: String) = repository.getMessages(chatName)
}