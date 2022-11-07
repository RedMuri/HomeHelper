package com.example.homehelper.presentation.adapters.services

import androidx.recyclerview.widget.RecyclerView
import com.example.homehelper.databinding.ItemEventBinding
import com.example.homehelper.databinding.ItemServiceBinding

class ServiceViewHolder(val binding: ItemServiceBinding) : RecyclerView.ViewHolder(binding.root) {
    val icon = binding.serviceIcon
    val name = binding.serviceName
}