package com.example.homehelper.presentation.adapters.users_for_message

import androidx.recyclerview.widget.RecyclerView
import com.example.homehelper.databinding.ItemUserMesBinding

class UserMesViewHolder(val binding: ItemUserMesBinding) : RecyclerView.ViewHolder(binding.root) {
    val name = binding.tvUserName
    val flatNum = binding.tvFlatNum
}