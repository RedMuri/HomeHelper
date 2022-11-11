package com.example.homehelper.domain.usecases.events

import com.example.homehelper.domain.repositories.FirebaseEventsRepository
import javax.inject.Inject

class DeleteEventUseCase @Inject constructor(
    private val repository: FirebaseEventsRepository
) {

    operator fun invoke(eventId: String) = repository.deleteEvent(eventId)
}