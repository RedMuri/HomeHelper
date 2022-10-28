package com.example.homehelper.presentation.screens

import android.os.Bundle
import android.os.Message
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.homehelper.data.firebase.FirebaseRepositoryImpl
import com.example.homehelper.databinding.FragmentChatBinding
import com.example.homehelper.domain.entities.Event
import com.example.homehelper.presentation.HomeHelperApp
import com.example.homehelper.presentation.adapters.messages.AdapterMessages
import com.example.homehelper.presentation.viewmodels.ChatViewModel
import com.example.homehelper.presentation.viewmodels.ViewModelFactory
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import javax.inject.Inject


class ChatFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var adapterMessages: AdapterMessages

    private val chatViewModel: ChatViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ChatViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as HomeHelperApp).component
    }

    private var _binding: FragmentChatBinding? = null
    private val binding: FragmentChatBinding
        get() = _binding ?: throw RuntimeException("FragmentChatBinding = null!")

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setOnClickListeners()
        observeViewModel()
    }

    private fun observeViewModel() {
        chatViewModel.getMessages().observe(viewLifecycleOwner) {
            adapterMessages.submitList(it) {
                binding.rvMessages.scrollToPosition(adapterMessages.itemCount - 1)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvMessages.adapter = adapterMessages
    }

    private fun setOnClickListeners() {
        binding.btAttachImageToMessage.setOnClickListener {
            Toast.makeText(requireActivity().application, "Image", Toast.LENGTH_SHORT).show()
        }
        binding.btSendMessage.setOnClickListener {
            sendMessage()
        }
    }

    private fun sendMessage() {
        val text = binding.etMessage.text.toString()
        val author = (requireActivity().application as HomeHelperApp).sharedPreferences
            .getString(HomeHelperApp.USER_NAME, "none") ?: "null"
        if (text.isNotBlank()) {
            chatViewModel.sendMessage(text, author)
            binding.etMessage.setText("")
        }
    }

    companion object {

        fun newInstance() =
            ChatFragment()
    }
}