package com.example.homehelper.domain.usecases

import com.example.homehelper.domain.FirebaseRepository
import javax.inject.Inject

class StartChatWIthSomeoneUseCase @Inject constructor(
    private val repository: FirebaseRepository
) {

    operator fun invoke(userEmail: String, someoneEmail: String) = repository.startChatWithSomeone(userEmail, someoneEmail)
}