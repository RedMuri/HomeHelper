package com.example.homehelper.presentation.adapters.payments

import androidx.recyclerview.widget.RecyclerView
import com.example.homehelper.databinding.ItemPaymentBinding
import com.example.homehelper.databinding.ItemServiceBinding

class PaymentsViewHolder(val binding: ItemPaymentBinding) : RecyclerView.ViewHolder(binding.root) {
    val date = binding.paymentDate
    val sum = binding.paymentSum
}