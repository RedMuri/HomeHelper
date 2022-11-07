package com.example.homehelper.presentation.screens.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.homehelper.databinding.FragmentChatListBinding
import com.example.homehelper.presentation.HomeHelperApp
import com.example.homehelper.presentation.adapters.chats.AdapterChats
import com.example.homehelper.presentation.screens.chats.ChatActivity
import com.example.homehelper.presentation.viewmodels.ChatsListViewModel
import com.example.homehelper.presentation.viewmodels.ViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import javax.inject.Inject


class ChatsListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val chatsListViewModel: ChatsListViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ChatsListViewModel::class.java]
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
        observeViewModel()
        setOnClickListeners()
        setupBottomSheet()
    }

    private fun setupBottomSheet() {
        val frontLayout = binding.frontLayout
        frontLayout.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                frontLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val peekHeight = countPeekHeight()
                val maxHeight = countMaxHeight()
                val behavior = BottomSheetBehavior.from(binding.contentLayout)
                behavior.maxHeight = maxHeight
                behavior.peekHeight = peekHeight
            }

            private fun countMaxHeight(): Int {
                val barrierMaxHeightLocation = intArrayOf(0, 0)
                binding.barrierMaxHeight.getLocationOnScreen(barrierMaxHeightLocation)
                val statusBarSize = binding.root.rootWindowInsets.systemWindowInsetTop
                val layoutHeight = binding.root.height
                return layoutHeight - barrierMaxHeightLocation[1] + statusBarSize
            }

            private fun countPeekHeight(): Int {
                val barrierPeekHeightLocation = intArrayOf(0, 0)
                binding.barrierPeekHeight.getLocationOnScreen(barrierPeekHeightLocation)
                val statusBarSize = binding.root.rootWindowInsets.systemWindowInsetTop
                val layoutHeight = binding.root.height
                return layoutHeight - barrierPeekHeightLocation[1] + statusBarSize
            }
        })
    }

    private fun setOnClickListeners() {
//        binding.fabNewChat.setOnClickListener {
//            val email =
//                (requireActivity().application as HomeHelperApp).sharedPreferences.getString(
//                    HomeHelperApp.USER_EMAIL, "none") ?: "none"
//            chatsListViewModel.startChatWithSomeone(email, HomeHelperApp.ADMIN_USER_NAME)
//        }
    }

    private fun observeViewModel() {
        val email = (requireActivity().application as HomeHelperApp).sharedPreferences.getString(
            HomeHelperApp.USER_EMAIL, "none")
        if (email != null) {
            chatsListViewModel.getChats(email).observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
        }
    }


    private fun setupRecyclerView() {
        val flatNum = (requireActivity().application as HomeHelperApp).sharedPreferences.getInt(
            HomeHelperApp.USER_FLAT_NUM,
            0)
        binding.rvChats.adapter = adapter
        adapter.onChatClickListener = {
            Log.i("muri", "chat : $it")
            startActivity(ChatActivity.newInstance(requireActivity().application,
                it.name.toString()))
        }
    }

    companion object {

        fun newInstance() =
            ChatsListFragment()
    }
}