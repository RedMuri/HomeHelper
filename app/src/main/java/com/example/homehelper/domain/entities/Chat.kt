package com.example.homehelper.domain.entities

import android.graphics.drawable.Drawable

data class Chat(
    var name: String? = null,
    var lastMessage: String? = null,
    var lastMessageAuthor: String? = null,
    var icon: Drawable? = null
)
