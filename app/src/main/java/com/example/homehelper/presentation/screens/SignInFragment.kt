package com.example.homehelper.presentation.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.homehelper.R
import com.example.homehelper.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    private val viewModelFactory by lazy {

    }

    private var _binding: FragmentSignInBinding? = null
    private val binding: FragmentSignInBinding
        get() = _binding ?: throw RuntimeException("FragmentSignInBinding = null!")

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("muri", "createUserWithEmail:success")
        binding.btSignIn.setOnClickListener{
            signIn()
        }
        binding.tvHaveAccount.setOnClickListener{
            launchLogInFragment()
        }

    }

    private fun launchLogInFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.auth_container,LogInFragment.newInstance())
            .commit()
    }

    private fun signIn(){
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.toString()

    }

    companion object {

        fun newInstance() = SignInFragment()
    }
}