package com.example.homehelper.presentation.screens.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import com.example.homehelper.R
import com.example.homehelper.databinding.FragmentServicesBinding
import com.example.homehelper.presentation.adapters.services.AdapterServices
import com.example.homehelper.presentation.adapters.services.Service
import com.example.homehelper.presentation.screens.services.ServiceActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior

class ServicesFragment : Fragment() {

    private val adapterServices by lazy {
        AdapterServices()
    }

    private var _binding: FragmentServicesBinding? = null
    private val binding: FragmentServicesBinding
        get() = _binding ?: throw RuntimeException("FragmentServicesBinding = null!")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentServicesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupBottomSheet()
//        binding.cvService1.setOnClickListener {
//            startActivity(ServiceActivity.newInstance(requireActivity().application,
//                ServiceActivity.SERVICE_PAYMENTS))
//        }
//        binding.cvService2.setOnClickListener {
//            startActivity(ServiceActivity.newInstance(requireActivity().application,
//                ServiceActivity.SERVICE_BILLS))
//        }
//        binding.cvService3.setOnClickListener {
//            startActivity(ServiceActivity.newInstance(requireActivity().application,
//                ServiceActivity.SERVICE_METERS))
//        }
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
        binding.rvServices.adapter = adapterServices
        val services = listOf(
            Service(R.drawable.main_service1, "Оплата счетов"),
            Service(R.drawable.main_service2, "Квитанции"),
            Service(R.drawable.main_service3, "Показания счётчиков"),
            Service(R.drawable.profile_info_members, "Аналитика возраста"),
            Service(R.drawable.profile_info_duration, "Аналитика пола"),
        )
        adapterServices.submitList(services)
        adapterServices.onServiceClickListener = {
            when (it.name) {
                "Оплата счетов" -> {
                    startActivity(ServiceActivity.newInstance(requireActivity().application,
                        ServiceActivity.SERVICE_PAYMENTS))
                }
                "Квитанции" -> {
                    startActivity(ServiceActivity.newInstance(requireActivity().application,
                        ServiceActivity.SERVICE_BILLS))
                }
                "Показания счётчиков" -> {
                    startActivity(ServiceActivity.newInstance(requireActivity().application,
                        ServiceActivity.SERVICE_METERS))
                }
                "Аналитика возраста" -> {
                    startActivity(ServiceActivity.newInstance(requireActivity().application,
                        ServiceActivity.SERVICE_ANALYTICS_AGE))
                }
                "Аналитика пола" -> {
                    startActivity(ServiceActivity.newInstance(requireActivity().application,
                        ServiceActivity.SERVICE_ANALYTICS_GENDER))
                }
            }
        }
    }


    companion object {

        fun newInstance() = ServicesFragment()
    }
}