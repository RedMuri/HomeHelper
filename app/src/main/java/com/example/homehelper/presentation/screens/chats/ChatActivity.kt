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
        val chatId = intent.getStringExtra(CHAT_ID).toString()
        val chatName = intent.getStringExtra(CHAT_NAME).toString()

        supportFragmentManager.beginTransaction()
            .replace(R.id.chat_fragment_container, ChatFragment.newInstance(chatId, chatName))
            .commit()
    }

    companion object {

        const val CHAT_ID = "chat_id"
        const val CHAT_NAME = "chat_name"

        fun newInstance(context: Context, chatId: String, chatName: String): Intent {
            return Intent(context, ChatActivity::class.java).apply {
                putExtra(CHAT_ID, chatId)
                putExtra(CHAT_NAME, chatName)
            }
        }
    }
}