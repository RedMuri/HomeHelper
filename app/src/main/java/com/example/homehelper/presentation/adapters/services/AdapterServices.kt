package com.example.homehelper.presentation.adapters.services

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.homehelper.databinding.ItemEventBinding
import com.example.homehelper.databinding.ItemServiceBinding
import com.example.homehelper.domain.entities.Event

class AdapterServices : ListAdapter<Service, ServiceViewHolder>(ServiceItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val binding = ItemServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ServiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val item = getItem(position)
        holder.icon.setImageResource(item.icon)
        holder.name.text = item.name
    }
}