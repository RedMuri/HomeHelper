package com.example.homehelper.presentation.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.homehelper.R

class ServiceActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)
        supportFragmentManager.beginTransaction()
            .replace(R.id.service_container, SendMeterDataFragment.newInstance()).commit()
    }

    companion object {

        fun newInstance(context: Context) = Intent(context, ServiceActivity::class.java)
    }
}