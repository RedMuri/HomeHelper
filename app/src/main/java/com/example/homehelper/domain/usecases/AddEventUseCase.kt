package com.example.homehelper.domain.usecases

import com.example.homehelper.domain.Event
import com.example.homehelper.domain.FirebaseRepository
import javax.inject.Inject


class AddEventUseCase @Inject constructor(
    private val repository: FirebaseRepository
) {

    operator fun invoke(event: Event) = repository.addEvent(event)
}