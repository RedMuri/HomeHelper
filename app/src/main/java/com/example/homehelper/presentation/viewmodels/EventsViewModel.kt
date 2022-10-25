package com.example.homehelper.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.homehelper.domain.Event
import com.example.homehelper.domain.usecases.AddEventUseCase
import com.example.homehelper.domain.usecases.GetEventsListUseCase
import javax.inject.Inject

class EventsViewModel @Inject constructor(
    private val addEventUseCase: AddEventUseCase,
    private val getEventsListUseCase: GetEventsListUseCase
): ViewModel() {

    fun addEvent(event: Event){
        addEventUseCase.invoke(event)
    }
}