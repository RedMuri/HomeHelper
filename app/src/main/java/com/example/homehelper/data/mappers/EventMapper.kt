package com.example.homehelper.data.mappers

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.homehelper.data.database.model.EventDbModel
import com.example.homehelper.data.firebase.model.EventDto
import com.example.homehelper.domain.entities.Event
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class EventMapper @Inject constructor() {

    fun mapEventDtoToEventDbModel(eventDto: EventDto): EventDbModel {
        return EventDbModel(
            title = eventDto.title,
            description = eventDto.description,
            date = eventDto.date,
            id = eventDto.id,
        )
    }

    private fun mapEventDbModelToEvent(eventDbModel: EventDbModel) = Event(
        title = eventDbModel.title,
        description = eventDbModel.description,
        date = eventDbModel.date,
        id = eventDbModel.id,
    )

    fun mapEventsDbModelToEvents(eventsDbModel: LiveData<List<EventDbModel>>): LiveData<List<Event>> =
        Transformations.map(eventsDbModel) { events ->
            events.map {
                mapEventDbModelToEvent(it)
            }
        }

    fun convertMlsToDate(mls: Long?): String {
        if (mls == null) return ""
        val timestamp = Timestamp(mls)
        val date = Date(timestamp.time)
        val pattern = "dd.MM.yy  HH:mm"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }
}