package com.example.homehelper.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.homehelper.databinding.ItemEventBinding
import com.example.homehelper.domain.Event

class AdapterEvents : ListAdapter<Event, EventViewHolder>(EventItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.tvEventTitle.text = item.title
        holder.binding.tvEventDesc.text = item.description
        holder.binding.tvEventDate.text = item.date.toString()
    }
}