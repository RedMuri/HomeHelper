package com.example.homehelper.presentation.adapters.chats

import androidx.recyclerview.widget.DiffUtil
import com.example.homehelper.domain.entities.Chat
import com.example.homehelper.domain.entities.Event

class ChatItemDiffCallback : DiffUtil.ItemCallback<Chat>() {

    override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
        return oldItem == newItem
    }
}