package com.example.homehelper.presentation.adapters.bills_history

import androidx.recyclerview.widget.DiffUtil

class BillHistoryItemDiffCallback : DiffUtil.ItemCallback<BillHistory>() {

    override fun areItemsTheSame(oldItem: BillHistory, newItem: BillHistory): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: BillHistory, newItem: BillHistory): Boolean {
        return oldItem == newItem
    }
}