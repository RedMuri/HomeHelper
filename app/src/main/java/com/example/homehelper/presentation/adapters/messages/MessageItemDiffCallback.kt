package com.example.homehelper.presentation.adapters.messages

import androidx.recyclerview.widget.DiffUtil
import com.example.homehelper.domain.entities.Event
import com.example.homehelper.domain.entities.Message

class MessageItemDiffCallback : DiffUtil.ItemCallback<Message>() {

    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem==newItem
    }
}