package com.example.homehelper.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.homehelper.di.ViewModelModule

class AuthViewModel: ViewModel() {

    private var _errorEmail = MutableLiveData<Boolean>()
    val errorEmail: LiveData<Boolean>
        get() = _errorEmail
    private var _errorPassword = MutableLiveData<Boolean>()
    val errorPassword: LiveData<Boolean>
        get() = _errorPassword

    fun signIn(){

    }
}