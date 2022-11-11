package com.example.homehelper.data.firebase.model

data class MeterDataDto(
    val userEmail: String = "none",
    val id: String = "none",
    val date: Long = 0,
    val value: Int = 0,
    val imageUrl: String = "none"
)