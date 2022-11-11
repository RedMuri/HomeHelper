package com.example.homehelper.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MetersDataViewModel @Inject constructor(
) : ViewModel() {

    private var _errorEmptyField = MutableLiveData<Unit>()
    val errorEmptyFiled: LiveData<Unit> = _errorEmptyField
    private var _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit> = _shouldCloseScreen

    fun addEvent(eventTitle: String, eventDesc: String) {
        if (eventTitle.isNotEmpty() && eventDesc.isNotEmpty()) {
            val date = System.currentTimeMillis()
            //addEventUseCase.invoke(eventTitle, eventDesc, date)
            _shouldCloseScreen.value = Unit
        } else {
            _errorEmptyField.value = Unit
        }
    }

    fun deleteEvent(eventId: String) {
    }

}