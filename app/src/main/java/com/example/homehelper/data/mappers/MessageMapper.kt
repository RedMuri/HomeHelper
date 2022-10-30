package com.example.homehelper.data.mappers

import com.example.homehelper.data.firebase.model.MessageDto
import com.example.homehelper.domain.entities.Message
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MessageMapper @Inject constructor() {

    fun messageDtoToEntity(messageDto: MessageDto) = Message(
        author = messageDto.author,
        message = messageDto.message,
        time = convertMlsToTime(messageDto.time),
        id = messageDto.id
    )

    private fun convertMlsToTime(mls: Long?): String {
        if (mls == null) return ""
        val timestamp = Timestamp(mls)
        val date = Date(timestamp.time)
        val pattern = "HH:mm"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }
}