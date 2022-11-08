package com.example.homehelper.presentation.adapters.payments

import androidx.recyclerview.widget.DiffUtil

class PaymentItemDiffCallback : DiffUtil.ItemCallback<Payment>() {

    override fun areItemsTheSame(oldItem: Payment, newItem: Payment): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Payment, newItem: Payment): Boolean {
        return oldItem == newItem
    }
}