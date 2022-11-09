package com.example.homehelper.domain.usecases.events

import com.example.homehelper.domain.repositories.FirebaseEventsRepository
import javax.inject.Inject

class GetEventsListUseCase @Inject constructor(
    private val repository: FirebaseEventsRepository
) {

    operator fun invoke() = repository.getEventsList()
}