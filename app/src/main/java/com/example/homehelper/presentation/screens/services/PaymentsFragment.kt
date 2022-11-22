package com.example.homehelper.presentation.screens.services

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.example.homehelper.databinding.FragmentPaymentsBinding
import com.example.homehelper.presentation.adapters.payments.AdapterPayments
import com.example.homehelper.presentation.adapters.payments.Payment
import com.google.android.material.bottomsheet.BottomSheetBehavior

class PaymentsFragment : Fragment() {

    private val adapterPayments by lazy {
        AdapterPayments()
    }

    private var _binding: FragmentPaymentsBinding? = null
    private val binding: FragmentPaymentsBinding
        get() = _binding ?: throw RuntimeException("FragmentPaymentsBinding = null!")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPaymentsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupBottomSheet()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btBack.setOnClickListener {
            requireActivity().finish()
        }
    }

    private fun setupBottomSheet() {
        val frontLayout = binding.frontLayout
        frontLayout.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                frontLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val peekHeight = countPeekHeight()
                val maxHeight = countMaxHeight()
                val behavior = BottomSheetBehavior.from(binding.contentLayout)
                behavior.maxHeight = maxHeight
                behavior.peekHeight = peekHeight
            }

            private fun countMaxHeight(): Int {
                val barrierMaxHeightLocation = intArrayOf(0, 0)
                binding.barrierMaxHeight.getLocationOnScreen(barrierMaxHeightLocation)
                val statusBarSize = binding.root.rootWindowInsets.systemWindowInsetTop
                val layoutHeight = binding.root.height
                return layoutHeight - barrierMaxHeightLocation[1] + statusBarSize
            }

            private fun countPeekHeight(): Int {
                val barrierPeekHeightLocation = intArrayOf(0, 0)
                binding.barrierPeekHeight.getLocationOnScreen(barrierPeekHeightLocation)
                val statusBarSize = binding.root.rootWindowInsets.systemWindowInsetTop
                val layoutHeight = binding.root.height
                return layoutHeight - barrierPeekHeightLocation[1] + statusBarSize
            }
        })
    }

    private fun setupRecyclerView() {
        binding.rvPayments.adapter = adapterPayments
        val payments = listOf(
            Payment(1,"28.10.2022","-4113,92P"),
            Payment(2,"28.10.2022","-4113,92P"),
            Payment(3,"28.10.2022","-4113,92P"),
            Payment(4,"28.10.2022","-4113,92P"),
            Payment(5,"28.10.2022","-4113,92P"),
            Payment(6,"28.10.2022","-4113,92P"),
            Payment(7,"28.10.2022","-4113,92P"),
            Payment(8,"28.10.2022","-4113,92P")
        )
        adapterPayments.submitList(payments)
    }

    companion object {

        fun newInstance() =
            PaymentsFragment()
    }
}