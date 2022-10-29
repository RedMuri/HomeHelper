package com.example.homehelper.presentation.screens.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.homehelper.R
import com.example.homehelper.databinding.FragmentEventsBinding
import com.example.homehelper.databinding.FragmentServicesBinding
import com.example.homehelper.presentation.screens.ServiceActivity

class ServicesFragment : Fragment() {

    private var _binding: FragmentServicesBinding? = null
    private val binding: FragmentServicesBinding
        get() = _binding ?: throw RuntimeException("FragmentServicesBinding = null!")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentServicesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            startActivity(ServiceActivity.newInstance(requireActivity().application))
        }
    }

    companion object {

        fun newInstance() = ServicesFragment()
    }
}