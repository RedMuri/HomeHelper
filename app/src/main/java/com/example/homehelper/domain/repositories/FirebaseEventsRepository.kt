package com.example.homehelper.domain.repositories

import androidx.lifecycle.LiveData
import com.example.homehelper.domain.entities.Event

interface FirebaseEventsRepository {

    fun getEvents(): LiveData<List<Event>>
    fun addEvent(title: String,desc: String, date: Long)
    fun deleteEvent(eventId: String)
    fun loadEventsFromFb()
}