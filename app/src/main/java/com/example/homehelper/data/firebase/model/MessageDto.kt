package com.example.homehelper.data.firebase.model

data class MessageDto(
    val author: String? = null,
    val message: String? = null,
    val time: Long? = null,
    var id: String? = null
)