package com.example.homehelper.presentation.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.homehelper.R
import com.example.homehelper.databinding.FragmentLogInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LogInFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    private var _binding: FragmentLogInBinding? = null
    private val binding: FragmentLogInBinding
        get() = _binding ?: throw RuntimeException("FragmentLogInBinding = null!")

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLogInBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("muri", "createUserWithEmail:success")
        setOnClickListeners()
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
            .replace(R.id.auth_container,SignInFragment.newInstance())
            .commit()
    }

    private fun logIn(){
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.toString().trim()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {task->
                if (task.isSuccessful) {
                    Log.d("muri", "logged:success")
                    val user = auth.currentUser
                } else {
                    Log.w("muri", "logged:failure", task.exception)
                    Toast.makeText(this.requireActivity().baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        } else{
            binding.tilEmail.error = "eroor"
            binding.tilPassword.error = "re"
        }
    }

    companion object {

        fun newInstance() = LogInFragment()
    }
}