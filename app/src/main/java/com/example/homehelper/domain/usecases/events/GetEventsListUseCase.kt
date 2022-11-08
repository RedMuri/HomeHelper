package com.example.homehelper.domain.usecases.events

import com.example.homehelper.domain.FirebaseRepository
import javax.inject.Inject

class GetEventsListUseCase @Inject constructor(
    private val repository: FirebaseRepository
) {

    operator fun invoke() = repository.getEventsList()
}