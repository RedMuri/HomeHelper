package com.example.homehelper.presentation.screens.addevent

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.homehelper.R
import com.example.homehelper.presentation.screens.chats.ChatActivity
import com.example.homehelper.presentation.screens.chats.ChatFragment

class EventDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)
        val eventTitle = intent.getStringExtra(EVENT_TITLE).toString()
        val eventDesc = intent.getStringExtra(EVENT_DESC).toString()

        supportFragmentManager.beginTransaction()
            .replace(R.id.chat_fragment_container,
                EventDetailFragment.newInstance(eventTitle, eventDesc))
            .commit()
    }


    companion object {

        const val EVENT_TITLE = "event_title"
        const val EVENT_DESC = "event_desc"

        fun newInstance(context: Context, eventTitle: String, eventDesc: String): Intent {
            return Intent(context, EventDetailActivity::class.java).apply {
                putExtra(EVENT_TITLE, eventTitle)
                putExtra(EVENT_DESC, eventDesc)
            }
        }
    }
}