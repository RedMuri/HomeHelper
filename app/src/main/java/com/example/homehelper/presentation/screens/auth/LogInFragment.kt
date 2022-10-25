package com.example.homehelper.presentation.screens.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.homehelper.R
import com.example.homehelper.databinding.FragmentLogInBinding
import com.example.homehelper.presentation.HomeHelperApp
import com.example.homehelper.presentation.viewmodels.AuthViewModel
import com.example.homehelper.presentation.viewmodels.ViewModelFactory
import javax.inject.Inject


class LogInFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: AuthViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as HomeHelperApp).component
    }

    private var _binding: FragmentLogInBinding? = null
    private val binding: FragmentLogInBinding
        get() = _binding ?: throw RuntimeException("FragmentLogInBinding = null!")

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
        observeViewModel()
        addTextChangeListeners()
    }

    private fun observeViewModel() {
        viewModel.errorEmail.observe(viewLifecycleOwner) {
            binding.tilEmail.error = when (it){
                AuthViewModel.ERROR_EMPTY -> "Input email"
                AuthViewModel.ERROR -> "Error"
                AuthViewModel.ERROR_WRONG_EMAIL -> "Wrong email format"
                AuthViewModel.ERROR_NOT_EXISTING_EMAIL -> "There is no user with such email"
                AuthViewModel.NO_ERRORS -> null
                else -> "Error"
            }
        }
        viewModel.errorPassword.observe(viewLifecycleOwner) {
            binding.tilPassword.error = when (it){
                AuthViewModel.ERROR_EMPTY -> "Input password"
                AuthViewModel.ERROR -> "Error"
                AuthViewModel.ERROR_WRONG_PASSWORD -> "Wrong password"
                AuthViewModel.ERROR_SHORT_PASSWORD -> "Password is too short (minimum is 6 characters)"
                AuthViewModel.NO_ERRORS -> null
                else -> "Error"
            }
        }
    }

    private fun addTextChangeListeners() {
        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputEmail()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputPassword()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun setOnClickListeners() {
        binding.btLogIn.setOnClickListener {
            logIn()
        }
        binding.tvNoAccount.setOnClickListener {
            launchSignInFragment()
        }
    }

    private fun launchSignInFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.auth_container, SignInFragment.newInstance())
            .commit()
    }

    private fun logIn() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        viewModel.logIn(email, password)
    }

    companion object {

        fun newInstance() = LogInFragment()
    }
}