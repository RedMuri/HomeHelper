package com.example.homehelper.presentation.screens.chats

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.homehelper.R

class ChatActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        val chatName = intent.getStringExtra(CHAT_NAME).toString()
        Log.i("muri", "chatActivity: ${intent.getStringExtra(CHAT_NAME)}")
        supportFragmentManager.beginTransaction()
            .replace(R.id.chat_fragment_container, ChatFragment.newInstance(chatName))
            .commit()
    }

    companion object {

        const val CHAT_NAME = "chat_name"

        fun newInstance(context: Context, chatName: String): Intent {
            return Intent(context, ChatActivity::class.java).apply {
                putExtra(CHAT_NAME, chatName)
            }
        }
    }
}