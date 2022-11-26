package com.example.homehelper.presentation.screens.events

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.homehelper.databinding.FragmentAddEventBinding
import com.example.homehelper.presentation.HomeHelperApp
import com.example.homehelper.presentation.viewmodels.EventsViewModel
import com.example.homehelper.presentation.viewmodels.ViewModelFactory
import javax.inject.Inject

class AddEventFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: EventsViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[EventsViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as HomeHelperApp).component
    }

    private var _binding: FragmentAddEventBinding? = null
    private val binding: FragmentAddEventBinding
        get() = _binding ?: throw RuntimeException("FragmentAddEventBinding = null!")


    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btSaveEvent.setOnClickListener {
            val title = binding.etEventTitle.text.toString().trim()
            val desc = binding.etEventDesc.text.toString().trim()
            viewModel.addEvent(title, desc)
        }
    }

    private fun observeViewModel() {
        viewModel.errorEmptyFiled.observe(viewLifecycleOwner) {
            Toast.makeText(requireActivity().application,
                "All fields must be filled!",
                Toast.LENGTH_SHORT).show()
        }
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner){
            requireActivity().finish()
        }
    }


    companion object {

        fun newInstance() =
            AddEventFragment()
    }
}