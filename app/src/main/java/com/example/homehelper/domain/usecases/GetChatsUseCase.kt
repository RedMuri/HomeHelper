package com.example.homehelper.domain.usecases

import com.example.homehelper.domain.FirebaseRepository
import javax.inject.Inject

class GetChatsUseCase @Inject constructor(
    private val repository: FirebaseRepository
) {

    operator fun invoke(userChats: List<String>) = repository.getChats(userChats)
}