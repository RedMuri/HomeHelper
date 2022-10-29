package com.example.homehelper.presentation.adapters.messages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.homehelper.databinding.ItemMyMessageBinding
import com.example.homehelper.databinding.ItemOtherMessageBinding
import com.example.homehelper.di.UserNameQualifier
import com.example.homehelper.domain.entities.Message
import javax.inject.Inject

class AdapterMessages @Inject constructor(
    @UserNameQualifier private val userName: String,
) : ListAdapter<Message, MessageViewHolder>(MessageItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = when (viewType) {
            MY_MESSAGE -> ItemMyMessageBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false)
            OTHER_MESSAGE -> ItemOtherMessageBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false)
            else -> throw RuntimeException("Unknown message type")
        }
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val item = getItem(position)
        when (holder.binding) {
            is ItemMyMessageBinding -> {
                holder.binding.tvMyMessage.text = item.message
                holder.binding.tvMyMessageTime.text = item.time
            }
            is ItemOtherMessageBinding -> {
                holder.binding.tvMessageAuthor.text = item.author
                holder.binding.tvOtherMessage.text = item.message
                holder.binding.tvOtherMessageTime.text = item.time
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = currentList[position]
        return if (message.author == userName)
            MY_MESSAGE
        else
            OTHER_MESSAGE
    }

    companion object {

        private const val MY_MESSAGE = 1
        private const val OTHER_MESSAGE = 0
    }
}