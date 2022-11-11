package com.example.homehelper.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.homehelper.domain.usecases.meters_data.SendMeterDataUseCase
import javax.inject.Inject

class MetersDataViewModel @Inject constructor(
    private val sendMeterDataUseCase: SendMeterDataUseCase,
) : ViewModel() {

    private var _errorEmptyField = MutableLiveData<Unit>()
    val errorEmptyFiled: LiveData<Unit> = _errorEmptyField
    private var _successSend = MutableLiveData<Unit>()
    val successSend: LiveData<Unit> = _successSend


    fun sendMeterData(meterDataValue: String, meterDataImage: String) {
        if (meterDataValue.isNotBlank()) {
            sendMeterDataUseCase.invoke(meterDataValue.toInt(), meterDataImage)
            _successSend.value = Unit
        }
        else
            _errorEmptyField.value = Unit
    }
}