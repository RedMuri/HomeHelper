package com.example.homehelper.presentation.adapters.bills_history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.homehelper.databinding.ItemBillHistoryBinding
import com.example.homehelper.presentation.adapters.payments.Payment

class AdapterBillsHistory : ListAdapter<BillHistory, BillsHistoryViewHolder>(BillHistoryItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillsHistoryViewHolder {
        val binding = ItemBillHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BillsHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BillsHistoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.date.text = item.date
    }
}