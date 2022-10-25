package com.example.homehelper.presentation.screens

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.homehelper.R

class AddEventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)
    }

    companion object{

        fun newInstance(context: Context): Intent {
            return Intent(context,AddEventActivity::class.java)
        }
    }
}