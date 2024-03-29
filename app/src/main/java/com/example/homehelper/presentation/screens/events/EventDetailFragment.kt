package com.example.homehelper.presentation.screens.events

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.homehelper.databinding.FragmentEventDetailBinding

class EventDetailFragment : Fragment() {

    private var _binding: FragmentEventDetailBinding? = null
    private val binding: FragmentEventDetailBinding
        get() = _binding ?: throw RuntimeException("FragmentEventDetailBinding = null!")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEventDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setEventDetails()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btBack.setOnClickListener {
            requireActivity().finish()
        }
    }

    private fun setEventDetails() {
        val eventTitle = arguments?.getString(EVENT_TITLE,"none")
        val eventDesc = arguments?.getString(EVENT_DESC,"none")
        binding.tvEventTitle.text = eventTitle
        binding.tvEventDesc.text = eventDesc
    }

    companion object {

        private const val EVENT_TITLE = "event_title"
        private const val EVENT_DESC = "event_desc"

        fun newInstance(eventTitle: String, eventDesc: String) =
            EventDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(EVENT_TITLE, eventTitle)
                    putString(EVENT_DESC, eventDesc)
                }
            }
    }
}