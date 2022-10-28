package com.example.homehelper.presentation.screens

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.homehelper.R
import com.example.homehelper.data.firebase.FirebaseRepositoryImpl
import com.example.homehelper.domain.usecases.GetMessagesUseCase
import com.example.homehelper.domain.usecases.SendMessageUseCase
import com.example.homehelper.presentation.viewmodels.ChatViewModel
import com.example.homehelper.presentation.viewmodels.ChatViewModel_Factory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class ChatActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
    }

    companion object {

        fun newInstance(context: Context): Intent {
            return Intent(context, ChatActivity::class.java)
        }
    }
}