package com.example.homehelper.presentation.adapters.chats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.homehelper.R
import com.example.homehelper.databinding.ItemChatBinding
import com.example.homehelper.domain.entities.Chat

class AdapterChats : ListAdapter<Chat, ChatViewHolder>(ChatItemDiffCallback()) {

    var onChatClickListener: ((Chat)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val item = getItem(position)
        holder.name.text = item.name
        holder.binding.root.setOnClickListener {
            onChatClickListener?.invoke(item)
        }
    }
}