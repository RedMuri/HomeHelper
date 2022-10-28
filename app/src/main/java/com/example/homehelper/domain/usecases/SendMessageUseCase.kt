package com.example.homehelper.domain.usecases

import com.example.homehelper.domain.FirebaseRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val repository: FirebaseRepository
) {

    operator fun invoke(text: String, author: String) = repository.sendMessage(text, author)
}