package com.example.homehelper.presentation.screens.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.homehelper.R
import com.example.homehelper.databinding.ActivityMainBinding
import com.example.homehelper.presentation.HomeHelperApp
import com.example.homehelper.presentation.screens.auth.AuthActivity
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    val settings by lazy {
        (application as HomeHelperApp).sharedPreferences
    }

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val component by lazy {
        (application as HomeHelperApp).component
    }

    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        bottomNavigate()
    }

    private fun bottomNavigate() {
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_main -> {
                    launchFragment(EventsFragment.newInstance())
                    true
                }
                R.id.action_services -> {
                    launchFragment(ServicesFragment.newInstance())
                    true
                }
                R.id.action_chats -> {
                    launchFragment(ChatsFragment.newInstance())
                    true
                }
                R.id.action_profile -> {
                    launchFragment(ProfileFragment.newInstance())
                    true
                }
                else -> true
            }
        }
        binding.bottomNav.selectedItemId = R.id.action_main
    }

    private fun launchFragment(fragment: Fragment) = supportFragmentManager.beginTransaction()
        .replace(R.id.main_container, fragment)
        .commit()

    override fun onStart() {
        super.onStart()
        checkUserSigned()
    }

    private fun checkUserSigned() {
        val currentUser = auth.currentUser
        Log.i("muri", settings.getString(HomeHelperApp.USER_NAME,"none").toString())
        if (currentUser == null)
            startActivity(AuthActivity.newIntent(application))
    }

    companion object {

        fun newInstance(context: Context): Intent{
            return Intent(context,MainActivity::class.java)
        }
    }
}