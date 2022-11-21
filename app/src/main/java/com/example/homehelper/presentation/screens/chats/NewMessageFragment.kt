package com.example.homehelper.presentation.screens.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.homehelper.R
import com.example.homehelper.databinding.FragmentChatBinding
import com.example.homehelper.presentation.HomeHelperApp
import com.example.homehelper.presentation.adapters.messages.AdapterMessages
import com.example.homehelper.presentation.viewmodels.ChatViewModel
import com.example.homehelper.presentation.viewmodels.ViewModelFactory
import javax.inject.Inject

class NewMessageFragment : Fragment() {

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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_new_message, container, false)
    }

    companion object {
        fun newInstance() =
            NewMessageFragment()
    }
}