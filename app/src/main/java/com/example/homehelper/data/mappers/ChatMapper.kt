package com.example.homehelper.data.mappers

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.homehelper.data.database.model.ChatDbModel
import com.example.homehelper.data.firebase.model.ChatDto
import com.example.homehelper.domain.entities.Chat
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ChatMapper @Inject constructor() {

    fun mapChatDtoToChatDbModel(chatDto: ChatDto) = ChatDbModel(
        id = chatDto.id,
        name = chatDto.name
    )


    private fun mapChatDbModelToChat(chatDbModel: ChatDbModel) = Chat(
        id = chatDbModel.id,
        name = chatDbModel.name
    )

    fun mapChatsDbModelToChats(chatsDbModel: LiveData<List<ChatDbModel>>): LiveData<List<Chat>> =
        Transformations.map(chatsDbModel) { chats ->
            chats.map {
                mapChatDbModelToChat(it)
            }
        }
}