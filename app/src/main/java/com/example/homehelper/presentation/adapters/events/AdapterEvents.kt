package com.example.homehelper.presentation.adapters.events

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.homehelper.databinding.ItemEventBinding
import com.example.homehelper.domain.entities.Event

class AdapterEvents : ListAdapter<Event, EventViewHolder>(EventItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val item = getItem(position)
        holder.title.text = item.title
        holder.desc.text = item.description
        holder.date.text = item.date.toString()
    }
}