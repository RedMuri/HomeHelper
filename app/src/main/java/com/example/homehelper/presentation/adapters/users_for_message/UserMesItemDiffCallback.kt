package com.example.homehelper.presentation.adapters.users_for_message

import androidx.recyclerview.widget.DiffUtil
import com.example.homehelper.domain.entities.User

class UserMesItemDiffCallback : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.email == newItem.email
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}