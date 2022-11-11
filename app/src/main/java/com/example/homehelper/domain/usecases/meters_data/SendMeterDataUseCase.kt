package com.example.homehelper.domain.usecases.meters_data

import com.example.homehelper.domain.repositories.FirebaseMetersDataRepository
import javax.inject.Inject

class SendMeterDataUseCase @Inject constructor(
    private val repository: FirebaseMetersDataRepository,
) {

    operator fun invoke(meterDataValue: Int, meterDataImage: String) =
        repository.sendMeterData(meterDataValue, meterDataImage)
}