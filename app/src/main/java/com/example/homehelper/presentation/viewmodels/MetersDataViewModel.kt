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
    private var _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit> = _shouldCloseScreen


    fun sendMeterData(meterDataValue: Int?, meterDataImage: String) {
        if (meterDataValue !=null) {
            sendMeterDataUseCase.invoke(meterDataValue, meterDataImage)
            _shouldCloseScreen.value = Unit
        }
        else
            _errorEmptyField.value = Unit
    }
}