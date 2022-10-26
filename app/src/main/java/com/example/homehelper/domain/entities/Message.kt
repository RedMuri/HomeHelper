package com.example.homehelper.domain.entities

data class Message(
    val author: String? = null,
    val message: String? = null,
    val date: Long? = null,
    val urlToImage: String? = null
)