package com.example.homehelper.data.firebase.model

data class EventDto(
    val title: String? = null,
    val description: String? = null,
    val date: String? = null,
    var id: String,
)