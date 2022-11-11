package com.example.homehelper.domain.repositories

interface FirebaseMetersDataRepository {

    fun sendMeterData(meterDataValue: Int, meterDataImage: String)
}