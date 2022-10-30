package com.example.homehelper.presentation.screens.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.homehelper.databinding.FragmentChatBinding
import com.example.homehelper.presentation.HomeHelperApp
import com.example.homehelper.presentation.adapters.messages.AdapterMessages
import com.example.homehelper.presentation.viewmodels.ChatViewModel
import com.example.homehelper.presentation.viewmodels.ViewModelFactory
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
        val chatName = arguments?.getString(ChatActivity.CHAT_NAME).toString()
        chatViewModel.getMessages(chatName).observe(viewLifecycleOwner) {
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
        val chatName = arguments?.getString(ChatActivity.CHAT_NAME).toString()
        val text = binding.etMessage.text.toString()
        val author = (requireActivity().application as HomeHelperApp).sharedPreferences
            .getString(HomeHelperApp.USER_EMAIL, "none") ?: "null"
        if (text.isNotBlank()) {
            chatViewModel.sendMessage(text, author,chatName)
            binding.etMessage.setText("")
        }
    }

    companion object {

        fun newInstance(chatName: String) =
            ChatFragment().apply {
                arguments = Bundle().apply {
                    putString(ChatActivity.CHAT_NAME, chatName)
                }
            }
    }
}