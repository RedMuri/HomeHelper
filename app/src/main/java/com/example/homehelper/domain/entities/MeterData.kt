package com.example.homehelper.domain.entities

data class MeterData(
    val userEmail: String,
    val id: String,
    val date: String,
    val value: Int,
    val imageUrl: String
)
