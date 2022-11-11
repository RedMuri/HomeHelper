package com.example.homehelper.domain.usecases.meters_data

import com.example.homehelper.domain.entities.MeterData
import com.example.homehelper.domain.repositories.FirebaseMetersDataRepository
import javax.inject.Inject

class SendMeterDataUseCase @Inject constructor(
    private val repository: FirebaseMetersDataRepository
) {

    operator fun invoke(meterData: MeterData) = repository.sendMeterData(meterData)
}