package com.example.homehelper.presentation.screens.services

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.homehelper.R
import com.example.homehelper.presentation.screens.analytics.AgeAnalyticsFragment
import com.example.homehelper.presentation.screens.analytics.GenderAnalyticsFragment

class ServiceActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)
        val fragment = when (intent.getStringExtra(SERVICE_NAME)) {
            SERVICE_PAYMENTS -> PaymentsFragment.newInstance()
            SERVICE_BILLS -> BillsFragment.newInstance()
            SERVICE_METERS -> MetersDataFragment.newInstance()
            SERVICE_ANALYTICS_AGE -> AgeAnalyticsFragment.newInstance()
            else -> GenderAnalyticsFragment.newInstance()
        }
        supportFragmentManager.beginTransaction().replace(R.id.service_container, fragment).commit()
    }

    companion object {

        const val SERVICE_NAME = "service_name"
        const val SERVICE_PAYMENTS = "1"
        const val SERVICE_BILLS = "2"
        const val SERVICE_METERS = "3"
        const val SERVICE_ANALYTICS_AGE = "4"
        const val SERVICE_ANALYTICS_GENDER = "5"

        fun newInstance(context: Context, serviceName: String) =
            Intent(context, ServiceActivity::class.java).apply {
                putExtra(SERVICE_NAME, serviceName)
            }
    }
}