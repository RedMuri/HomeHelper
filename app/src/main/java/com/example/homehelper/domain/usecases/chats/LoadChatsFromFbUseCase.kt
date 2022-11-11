package com.example.homehelper.domain.usecases.chats

import com.example.homehelper.domain.repositories.FirebaseChatsRepository
import com.example.homehelper.domain.repositories.FirebaseEventsRepository
import javax.inject.Inject


class LoadChatsFromFbUseCase @Inject constructor(
    private val repository: FirebaseChatsRepository,
) {

    operator fun invoke(userEmail: String) =
        repository.loadUserChatsFromFb(userEmail)
}