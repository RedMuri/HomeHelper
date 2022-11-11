package com.example.homehelper.data.firebase.repositories_impl

import android.app.Application
import android.util.Log
import com.example.homehelper.data.firebase.model.MessageDto
import com.example.homehelper.data.firebase.model.MeterDataDto
import com.example.homehelper.domain.repositories.FirebaseMetersDataRepository
import com.example.homehelper.presentation.HomeHelperApp
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FirebaseMetersDataRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val application: Application
): FirebaseMetersDataRepository {

    override fun sendMeterData(meterDataValue: Int, meterDataImage: String) {
        val id = db.collection(METERS_DATA).document(LIGHT).collection(LIGHT_METERS_DATA).document().id
        val date = System.currentTimeMillis()
        val userEmail = (application as HomeHelperApp).getUserEmail()
        val meterDataDto = MeterDataDto(userEmail,id,date,meterDataValue, meterDataImage)

        db.collection(METERS_DATA).document(LIGHT).collection(LIGHT_METERS_DATA)
            .document(id)
            .set(meterDataDto)
            .addOnCompleteListener {
                if (it.isSuccessful)
                    Log.i("muri", "sendMeterData success: $it")
                else
                    Log.i("muri", "sendMeterData failure: $it")
            }.addOnFailureListener { e ->
                Log.i("muri", "sendMeterData error: $e")
            }
    }

    companion object {

        const val METERS_DATA = "meters_data"
        const val LIGHT = "light"
        const val LIGHT_METERS_DATA = "light_meters_data"
    }
}