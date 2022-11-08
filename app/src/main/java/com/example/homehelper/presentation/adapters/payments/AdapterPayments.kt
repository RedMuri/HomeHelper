package com.example.homehelper.presentation.adapters.payments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.homehelper.databinding.ItemPaymentBinding

class AdapterPayments : ListAdapter<Payment, PaymentsViewHolder>(PaymentItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentsViewHolder {
        val binding = ItemPaymentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PaymentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PaymentsViewHolder, position: Int) {
        val item = getItem(position)
        holder.date.text = item.date
        holder.sum.text = item.sum
    }
}