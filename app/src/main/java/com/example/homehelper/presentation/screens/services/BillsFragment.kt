package com.example.homehelper.presentation.screens.services

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import com.example.homehelper.databinding.FragmentBillsBinding
import com.example.homehelper.presentation.adapters.bills_history.AdapterBillsHistory
import com.example.homehelper.presentation.adapters.bills_history.BillHistory
import com.google.android.material.bottomsheet.BottomSheetBehavior

class BillsFragment : Fragment() {

    private val adapterBillHistory by lazy {
        AdapterBillsHistory()
    }

    private var _binding: FragmentBillsBinding? = null
    private val binding: FragmentBillsBinding
        get() = _binding ?: throw RuntimeException("FragmentBillsBinding = null!")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBillsBinding.inflate(inflater, container, false)
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
        binding.rvBillsHistory.adapter = adapterBillHistory
        val payments = listOf(
            BillHistory(1, "28.10.2022"),
            BillHistory(2, "28.10.2022"),
            BillHistory(3, "28.10.2022"),
            BillHistory(4, "28.10.2022"),
            BillHistory(5, "28.10.2022"),
            BillHistory(6, "28.10.2022"),
            BillHistory(7, "28.10.2022"),
            BillHistory(8, "28.10.2022")
        )
        adapterBillHistory.submitList(payments)
    }

    companion object {

        fun newInstance() =
            BillsFragment()

    }
}