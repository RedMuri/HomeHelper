package com.example.homehelper.presentation.screens.main

import android.os.Bundle
import android.util.Log
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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
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
        binding.rvEvents.adapter = adapterEvents

        val db = FirebaseFirestore.getInstance()
        db.collection("events")
            .orderBy("date")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("muri", "Listen failed.", e)
                    return@addSnapshotListener
                }
                Log.d("muri", "Current value is: ${value?.documentChanges}")
                try {
                    val events = value?.map { it.toObject<Event>() }
                    adapterEvents.submitList(events)
                } catch (e: Exception) {
                    Log.i("muri", "Exception: $e")
                }
            }
    }

    companion object {

        fun newInstance() = EventsFragment()
    }
}