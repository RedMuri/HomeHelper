package com.example.homehelper.presentation.screens.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.homehelper.databinding.FragmentEventsBinding
import com.example.homehelper.presentation.HomeHelperApp
import com.example.homehelper.presentation.adapters.events.AdapterEvents
import com.example.homehelper.presentation.screens.ServiceActivity
import com.example.homehelper.presentation.screens.addevent.AddEventActivity
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

    private val adapterEvents by lazy {
        AdapterEvents()
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
        observeViewModel()
        checkIfAdmin()
        binding.mainLl1.setOnClickListener {
            startActivity(ServiceActivity.newInstance(requireActivity().application,
                ServiceActivity.SERVICE_PAYMENTS))
        }
        binding.mainLl2.setOnClickListener {
            startActivity(ServiceActivity.newInstance(requireActivity().application,
                ServiceActivity.SERVICE_BILLS))
        }
        binding.mainLl3.setOnClickListener {
            startActivity(ServiceActivity.newInstance(requireActivity().application,
                ServiceActivity.SERVICE_METERS))
        }
    }

    private fun checkIfAdmin() {
        val userName = (requireActivity() as MainActivity).sharedPreferences.getString(HomeHelperApp.USER_EMAIL,"none")
//        if (userName == HomeHelperApp.ADMIN_USER_NAME){
//            binding.fabAddEvent.visibility = View.VISIBLE
//            setupSwipeListener(binding.rvEvents)
//            binding.fabAddEvent.setOnClickListener {
//                startActivity(AddEventActivity.newInstance(requireActivity().application))
//            }
//        }
    }

    private fun observeViewModel() {
        viewModel.getEventsList().observe(viewLifecycleOwner) {
            adapterEvents.submitList(it)
        }
    }

    private fun setupRecyclerView() {
        binding.rvEvents.adapter = adapterEvents
    }

    private fun setupSwipeListener(recyclerView: RecyclerView) {
        val itemTouchHelper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapterEvents.currentList[viewHolder.adapterPosition]
                item.id?.let { viewModel.deleteEvent(it) }
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    //TODO add onDestroy() everywhere

    companion object {

        fun newInstance() = EventsFragment()
    }
}