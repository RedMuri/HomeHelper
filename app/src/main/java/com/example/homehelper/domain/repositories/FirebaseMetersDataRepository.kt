package com.example.homehelper.domain.repositories

import com.example.homehelper.domain.entities.MeterData

interface FirebaseMetersDataRepository {

    fun sendMeterData(meterData: MeterData)
}