package com.example.homehelper.presentation.screens.services

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.homehelper.R
import com.example.homehelper.databinding.FragmentEventsBinding
import com.example.homehelper.databinding.FragmentMetersDataBinding
import com.example.homehelper.presentation.HomeHelperApp
import com.example.homehelper.presentation.adapters.events.AdapterEvents
import com.example.homehelper.presentation.viewmodels.EventsViewModel
import com.example.homehelper.presentation.viewmodels.MetersDataViewModel
import com.example.homehelper.presentation.viewmodels.ViewModelFactory
import javax.inject.Inject

class MetersDataFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: MetersDataViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MetersDataViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as HomeHelperApp).component
    }

    private var _binding: FragmentMetersDataBinding? = null
    private val binding: FragmentMetersDataBinding
        get() = _binding ?: throw RuntimeException("FragmentMetersDataBinding = null!")

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMetersDataBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btPay.setOnClickListener {
            val value = binding.lightEtMeterData.text.toString().trim()
            viewModel.sendMeterData(value, "image")
        }
        binding.btBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun observeViewModel() {
        viewModel.errorEmptyFiled.observe(viewLifecycleOwner) {
            Toast.makeText(requireActivity().application,
                "All fields must be filled!",
                Toast.LENGTH_SHORT).show()
        }
        viewModel.successSend.observe(viewLifecycleOwner){
            Toast.makeText(requireActivity().application,
                "Successfully sent!",
                Toast.LENGTH_SHORT).show()
            binding.lightEtMeterData.setText("")
        }
    }

    companion object {

        fun newInstance() =
            MetersDataFragment()
    }
}