package com.example.homehelper.presentation.adapters.payments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.homehelper.databinding.ItemServiceBinding

class AdapterPayments : ListAdapter<Payment, PaymentsViewHolder>(PaymentItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentsViewHolder {
        val binding = ItemServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PaymentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PaymentsViewHolder, position: Int) {
        val item = getItem(position)
        holder.icon.setImageResource(item.icon)
        holder.name.text = item.name
    }
}