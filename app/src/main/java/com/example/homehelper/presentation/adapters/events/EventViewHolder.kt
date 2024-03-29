package com.example.homehelper.presentation.adapters.events

import androidx.recyclerview.widget.RecyclerView
import com.example.homehelper.databinding.ItemEventBinding

class EventViewHolder(val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
    val title = binding.tvEventTitle
    val date = binding.tvEventDate
    val btReadMore = binding.btReadMore
}