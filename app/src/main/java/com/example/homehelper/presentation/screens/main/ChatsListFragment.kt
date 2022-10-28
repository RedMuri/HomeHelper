package com.example.homehelper.presentation.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.homehelper.databinding.FragmentChatListBinding
import com.example.homehelper.domain.entities.Chat
import com.example.homehelper.presentation.HomeHelperApp
import com.example.homehelper.presentation.adapters.chats.AdapterChats
import com.example.homehelper.presentation.screens.ChatActivity
import com.example.homehelper.presentation.viewmodels.EventsViewModel
import com.example.homehelper.presentation.viewmodels.ViewModelFactory
import javax.inject.Inject


class ChatsListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val chatViewModel: EventsViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[EventsViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as HomeHelperApp).component
    }

    private val adapter by lazy {
        AdapterChats()
    }

    private var _binding: FragmentChatListBinding? = null
    private val binding: FragmentChatListBinding
        get() = _binding ?: throw RuntimeException("FragmentChatListBinding = null!")

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentChatListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvChats.adapter = adapter
        val chat = Chat("Main","Last message...","Author:")
        adapter.submitList(listOf(chat))
        adapter.onChatClickListener = {
            startActivity(ChatActivity.newInstance(requireActivity().application))
        }
    }

    companion object {

        fun newInstance() =
            ChatsListFragment()
    }
}