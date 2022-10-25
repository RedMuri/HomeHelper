package com.example.homehelper.presentation.screens

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.homehelper.R
import com.example.homehelper.databinding.FragmentSignInBinding
import com.example.homehelper.presentation.HomeHelperApp
import com.example.homehelper.presentation.viewmodels.AuthViewModel
import com.example.homehelper.presentation.viewmodels.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class SignInFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: AuthViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as HomeHelperApp).component
    }

    private var _binding: FragmentSignInBinding? = null
    private val binding: FragmentSignInBinding
        get() = _binding ?: throw RuntimeException("FragmentSignInBinding = null!")

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
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
                AuthViewModel.ERROR_SUCH_USER_EXIST -> "User with such email already exist"
                AuthViewModel.ERROR_EMPTY -> "Input email"
                AuthViewModel.ERROR -> "Error"
                AuthViewModel.ERROR_WRONG_EMAIL -> "Wrong email format"
                AuthViewModel.NO_ERRORS -> null
                else -> "Error"
            }
        }
        viewModel.errorPassword.observe(viewLifecycleOwner) {
            binding.tilPassword.error = when (it){
                AuthViewModel.ERROR_EMPTY -> "Input password"
                AuthViewModel.ERROR -> "Error"
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
        binding.btSignIn.setOnClickListener {
            signIn()
        }
        binding.tvHaveAccount.setOnClickListener {
            launchLogInFragment()
        }
    }

    private fun launchLogInFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.auth_container, LogInFragment.newInstance())
            .commit()
    }

    private fun signIn() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        viewModel.signIn(email, password)
    }

    companion object {

        fun newInstance() = SignInFragment()
    }
}