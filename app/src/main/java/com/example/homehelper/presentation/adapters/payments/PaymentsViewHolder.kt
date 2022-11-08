package com.example.homehelper.presentation.adapters.payments

import androidx.recyclerview.widget.RecyclerView
import com.example.homehelper.databinding.ItemServiceBinding

class PaymentsViewHolder(val binding: ItemServiceBinding) : RecyclerView.ViewHolder(binding.root) {
    val icon = binding.serviceIcon
    val name = binding.serviceName
}