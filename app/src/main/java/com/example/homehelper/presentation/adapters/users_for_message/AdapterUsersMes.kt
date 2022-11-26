package com.example.homehelper.presentation.adapters.users_for_message

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.homehelper.databinding.ItemUserMesBinding
import com.example.homehelper.domain.entities.User
import com.example.homehelper.presentation.adapters.users_for_message.UserMesItemDiffCallback
import com.example.homehelper.presentation.adapters.users_for_message.UserMesViewHolder
import javax.inject.Inject

class AdapterUsersMes @Inject constructor() :
    ListAdapter<User, UserMesViewHolder>(UserMesItemDiffCallback()) {

    var onUserClickListener: ((User) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserMesViewHolder {
        val binding = ItemUserMesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserMesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserMesViewHolder, position: Int) {
        val item = getItem(position)
        holder.name.text = item.email
        holder.flatNum.text = item.flat.toString()
        holder.binding.root.setOnClickListener {
            onUserClickListener?.invoke(item)
        }
    }
}