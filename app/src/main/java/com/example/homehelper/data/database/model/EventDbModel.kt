package com.example.homehelper.data.database.model

import com.example.homehelper.data.database.AppDatabase
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = AppDatabase.EVENTS_TABLE_NAME)
data class EventDbModel(
    val title: String,
    val description: String,
    val date: String,
    @PrimaryKey
    val id: String,
)