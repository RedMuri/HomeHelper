package com.example.homehelper.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homehelper.domain.entities.Event
import com.example.homehelper.domain.usecases.events.AddEventUseCase
import com.example.homehelper.domain.usecases.events.DeleteEventUseCase
import com.example.homehelper.domain.usecases.events.GetEventsUseCase
import com.example.homehelper.domain.usecases.events.LoadEventsFromFbUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventsViewModel @Inject constructor(
    private val addEventUseCase: AddEventUseCase,
    private val getEventsUseCase: GetEventsUseCase,
    private val deleteEventUseCase: DeleteEventUseCase,
    private val loadEventsFromFbUseCase: LoadEventsFromFbUseCase
) : ViewModel() {

    private var _errorEmptyField = MutableLiveData<Unit>()
    val errorEmptyFiled: LiveData<Unit>
        get() = _errorEmptyField
    private var _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    init {
        viewModelScope.launch(Dispatchers.IO){
            loadEventsFromFbUseCase.invoke()
        }
    }

    fun addEvent(eventTitle: String, eventDesc: String) {
        if (eventTitle.isNotEmpty() && eventDesc.isNotEmpty()) {
            val date = System.currentTimeMillis()
            addEventUseCase.invoke(eventTitle, eventDesc, date)
            _shouldCloseScreen.value = Unit
        } else {
            _errorEmptyField.value = Unit
        }
    }

    fun deleteEvent(eventId: String){
        deleteEventUseCase.invoke(eventId)
    }

    fun getEvents(): LiveData<List<Event>> {
        return getEventsUseCase.invoke()
    }
}