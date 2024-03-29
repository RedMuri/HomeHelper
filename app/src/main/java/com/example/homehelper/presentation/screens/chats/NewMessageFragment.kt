package com.example.homehelper.presentation.screens.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.homehelper.databinding.FragmentNewMessageBinding
import com.example.homehelper.presentation.HomeHelperApp
import com.example.homehelper.presentation.adapters.users_for_message.AdapterUsersMes
import com.example.homehelper.presentation.viewmodels.ChatsListViewModel
import com.example.homehelper.presentation.viewmodels.UsersListViewModel
import com.example.homehelper.presentation.viewmodels.ViewModelFactory
import javax.inject.Inject

class NewMessageFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var adapterUsersMes: AdapterUsersMes

    private val usersListViewModel: UsersListViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[UsersListViewModel::class.java]
    }

    private val chatsListViewModel: ChatsListViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ChatsListViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as HomeHelperApp).component
    }

    private var _binding: FragmentNewMessageBinding? = null
    private val binding: FragmentNewMessageBinding
        get() = _binding ?: throw RuntimeException("FragmentNewMessageBinding = null!")

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNewMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupRecyclerView()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun observeViewModel() {
        usersListViewModel.getAllUsers().observe(viewLifecycleOwner) {
            adapterUsersMes.submitList(it)
        }
    }

    private fun setupRecyclerView() {
        binding.rvUsers.adapter = adapterUsersMes
        adapterUsersMes.onUserClickListener = { it ->
            val userEmail = (requireActivity().application as HomeHelperApp).getUserEmail()
            chatsListViewModel.startChatWithSomeone(userEmail, it.email){ chatDto ->
                startActivity(ChatActivity.newInstance(requireActivity().application,
                    chatDto.id,chatDto.name.substringAfter("||")))
//                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }


    companion object {
        fun newInstance() =
            NewMessageFragment()
    }
}