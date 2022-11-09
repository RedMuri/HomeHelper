package com.example.homehelper.data.database.model

import com.example.homehelper.data.database.AppDatabase
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = AppDatabase.EVENTS_TABLE_NAME)
data class EventDbModel(
    @PrimaryKey
    var id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val date: String? = null
)