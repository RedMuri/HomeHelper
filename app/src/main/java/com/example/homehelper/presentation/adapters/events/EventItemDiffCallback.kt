package com.example.homehelper.presentation.adapters.events

import androidx.recyclerview.widget.DiffUtil
import com.example.homehelper.domain.entities.Event

class EventItemDiffCallback : DiffUtil.ItemCallback<Event>() {

    override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem == newItem
    }
}