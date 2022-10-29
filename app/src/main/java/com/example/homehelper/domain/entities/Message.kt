package com.example.homehelper.domain.entities

data class Message(
    val author: String? = null,
    val message: String? = null,
    val time: String? = null,
    var id: String? = null
)