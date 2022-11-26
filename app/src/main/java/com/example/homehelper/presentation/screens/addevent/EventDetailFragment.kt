package com.example.homehelper.presentation.screens.addevent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.homehelper.R
import com.example.homehelper.databinding.FragmentAddEventBinding
import com.example.homehelper.databinding.FragmentEventDetailBinding
import com.example.homehelper.domain.entities.Event

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

    }

    companion object {

        private const val EVENT_TITLE = "event"
        private const val EVENT_DESC = "event"

        fun newInstance(eventTitle: String, eventDesc: String) =
            EventDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(EVENT_TITLE, eventTitle)
                    putString(EVENT_DESC, eventDesc)
                }
            }
    }
}