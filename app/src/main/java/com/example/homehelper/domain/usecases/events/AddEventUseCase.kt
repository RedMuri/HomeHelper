package com.example.homehelper.domain.usecases.events

import com.example.homehelper.domain.FirebaseRepository
import com.example.homehelper.domain.usecases.repositories.FirebaseEventsRepository
import javax.inject.Inject


class AddEventUseCase @Inject constructor(
    private val repository: FirebaseEventsRepository,
) {

    operator fun invoke(title: String, desc: String, date: Long) =
        repository.addEvent(title, desc, date)
}