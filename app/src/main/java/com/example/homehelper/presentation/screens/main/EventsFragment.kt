package com.example.homehelper.presentation.screens.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.homehelper.databinding.FragmentEventsBinding
import com.example.homehelper.domain.Event
import com.example.homehelper.presentation.HomeHelperApp
import com.example.homehelper.presentation.adapters.AdapterEvents
import com.example.homehelper.presentation.screens.AddEventActivity
import com.example.homehelper.presentation.viewmodels.EventsViewModel
import com.example.homehelper.presentation.viewmodels.ViewModelFactory
import javax.inject.Inject

class EventsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: EventsViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[EventsViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as HomeHelperApp).component
    }

    private var _binding: FragmentEventsBinding? = null
    private val binding: FragmentEventsBinding
        get() = _binding ?: throw RuntimeException("FragmentEventsBinding = null!")

    //-----------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        binding.fabAddEvent.setOnClickListener {
            startActivity(AddEventActivity.newInstance(requireActivity().application))
        }
    }

    private fun setupRecyclerView() {
        val adapterEvents = AdapterEvents()
        val events = listOf(Event(1,
            "Open close doors",
            "What you need to do is pass the generated binding class object to the holder class constructor. In below example, I have row_payment XML file for RecyclerView item and the generated class is RowPaymentBinding so like this",
            292929),Event(1,
            "Walking dogs",
            "Can I use ViewBindings to replace findViewById in this typical RecyclerView.Adapter initialization code?",
            383294))
        binding.rvEvents.adapter = adapterEvents
        adapterEvents.submitList(events)
    }

    companion object {

        fun newInstance() = EventsFragment()
    }
}