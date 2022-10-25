package com.example.homehelper.presentation.viewmodels

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
    private val getEventsListUseCase: GetEventsListUseCase
): ViewModel() {

    fun addEvent(eventTitle: String, eventDesc: String){
        if (eventTitle.isNotBlank() && eventDesc.isNotBlank()){
            val date = convertMls(System.currentTimeMillis())
            val event = Event(eventTitle,eventDesc,date)
            addEventUseCase.invoke(event)
        }
    }

    private fun convertMls(mls: Long?): String {
        if (mls == null) return ""
        val timestamp = Timestamp(mls)
        val date = Date(timestamp.time)
        val pattern = "HH:mm:ss"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }
}