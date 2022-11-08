package com.example.homehelper.domain.usecases.events

import com.example.homehelper.domain.FirebaseRepository
import javax.inject.Inject

class DeleteEventUseCase @Inject constructor(
    private val repository: FirebaseRepository
) {

    operator fun invoke(eventId: String) = repository.deleteEvent(eventId)
}