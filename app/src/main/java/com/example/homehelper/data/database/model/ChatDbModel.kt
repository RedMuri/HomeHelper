package com.example.homehelper.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.homehelper.data.database.AppDatabase

@Entity(tableName = AppDatabase.CHATS_TABLE_NAME)
data class ChatDbModel(
    @PrimaryKey
    val id: String,
    val name: String
)