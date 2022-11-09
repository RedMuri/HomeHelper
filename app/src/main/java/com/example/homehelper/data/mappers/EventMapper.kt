package com.example.homehelper.data.mappers

import com.example.homehelper.data.database.model.EventDbModel
import com.example.homehelper.data.firebase.model.EventDto
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class EventMapper @Inject constructor() {

    fun convertMlsToDate(mls: Long?): String {
        if (mls == null) return ""
        val timestamp = Timestamp(mls)
        val date = Date(timestamp.time)
        val pattern = "dd.MM.yy  HH:mm"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    fun mapEventDtoToEventDbModel(eventDto: EventDto) = EventDbModel(
        title = eventDto.title,
        description = eventDto.description,
        date = eventDto.date,
        id = eventDto.id,
    )
}