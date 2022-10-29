package com.example.homehelper.presentation.screens.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.homehelper.databinding.FragmentProfileBinding
import com.example.homehelper.presentation.HomeHelperApp
import com.example.homehelper.presentation.screens.auth.AuthActivity
import com.example.homehelper.presentation.viewmodels.AuthViewModel
import com.example.homehelper.presentation.viewmodels.ViewModelFactory
import javax.inject.Inject

class ProfileFragment : Fragment() {


    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val authViewModel: AuthViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as HomeHelperApp).component
    }

    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding
        get() = _binding ?: throw RuntimeException("FragmentProfileBinding = null!")

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btSignOut.setOnClickListener {
            authViewModel.signOut()
            startActivity(AuthActivity.newIntent(requireActivity().application))
            (requireActivity().application as HomeHelperApp).sharedPreferences.edit()
                .putString(HomeHelperApp.USER_EMAIL, "none")
                .putInt(HomeHelperApp.USER_FLAT_NUM, 0)
                .apply()
        }
    }

    companion object {

        fun newInstance() =
            ProfileFragment()
    }
}