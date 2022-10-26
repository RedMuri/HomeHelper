package com.example.homehelper.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.homehelper.domain.Event
import com.example.homehelper.domain.usecases.AddEventUseCase
import com.example.homehelper.domain.usecases.GetEventsListUseCase
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class EventsViewModel @Inject constructor(
    private val addEventUseCase: AddEventUseCase,
    private val getEventsListUseCase: GetEventsListUseCase,
) : ViewModel() {

    private var _errorEmptyField = MutableLiveData<Unit>()
    val errorEmptyFiled: LiveData<Unit>
        get() = _errorEmptyField
    private var _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    fun addEvent(eventTitle: String, eventDesc: String) {
        if (eventTitle.isNotEmpty() && eventDesc.isNotEmpty()) {
            val date = System.currentTimeMillis()
            addEventUseCase.invoke(eventTitle, eventDesc, date)
            _shouldCloseScreen.value = Unit
        } else {
            _errorEmptyField.value = Unit
        }
    }

    fun getEventsList(): LiveData<List<Event>> {
        return getEventsListUseCase.invoke()
    }
}