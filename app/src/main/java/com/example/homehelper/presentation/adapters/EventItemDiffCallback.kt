package com.example.homehelper.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.homehelper.domain.Event

class EventItemDiffCallback : DiffUtil.ItemCallback<Event>() {

    override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem == newItem
    }
}