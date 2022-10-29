package com.example.homehelper.domain.entities

data class User(
    val email: String? = null,
    val flatNum: Int? = null,
    val chats: List<String>? = null
)
