package com.example.homehelper.presentation.adapters.chats

import androidx.recyclerview.widget.RecyclerView
import com.example.homehelper.databinding.ItemChatBinding
import com.example.homehelper.databinding.ItemEventBinding

class ChatViewHolder(val binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root) {
    val name = binding.tvChatName
    val lastMessage = binding.tvLastMessage
    val lastMessageAuthor = binding.tvLastMessageAuthor
}