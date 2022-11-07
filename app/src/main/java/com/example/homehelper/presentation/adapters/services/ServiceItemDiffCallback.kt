package com.example.homehelper.presentation.adapters.services

import androidx.recyclerview.widget.DiffUtil
import com.example.homehelper.domain.entities.Event

class ServiceItemDiffCallback : DiffUtil.ItemCallback<Service>() {

    override fun areItemsTheSame(oldItem: Service, newItem: Service): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Service, newItem: Service): Boolean {
        return oldItem == newItem
    }
}