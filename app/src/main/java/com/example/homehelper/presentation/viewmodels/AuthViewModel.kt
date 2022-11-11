package com.example.homehelper.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.homehelper.domain.usecases.auth.GetFirebaseAuthUseCase
import com.example.homehelper.domain.usecases.auth.LogInUseCase
import com.example.homehelper.domain.usecases.auth.SignInUseCase
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val getFirebaseAuthUseCase: GetFirebaseAuthUseCase,
    private val signInUseCase: SignInUseCase,
    private val logInUseCase: LogInUseCase,
) : ViewModel() {

    private var _errorEmail = MutableLiveData<String>()
    val errorEmail: LiveData<String> = _errorEmail
    private var _errorPassword = MutableLiveData<String>()
    val errorPassword: LiveData<String> = _errorPassword
    private var _errorFlatNum = MutableLiveData<String>()
    val errorFlatNum: LiveData<String> = _errorFlatNum
    private var _errorNetwork = MutableLiveData<Unit>()
    val errorNetwork: LiveData<Unit> = _errorNetwork
    private var _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    fun signIn(inputEmail: String, inputPassword: String, flatNum: String) {
        val fieldsValid = validateInputSignIn(inputEmail, inputPassword, flatNum)
        if (fieldsValid) {
            signInUseCase(inputEmail, inputPassword, flatNum.toInt()).addOnCompleteListener {
                if (it.isSuccessful) {
                    _userName.value = inputEmail
                } else {
                    Log.i("muri", "fail: ${it.exception}")
                    checkSignInException(it)
                }
            }
        }
    }

    private fun checkSignInException(it: Task<AuthResult>) {
        when (it.exception) {
            is FirebaseAuthInvalidCredentialsException -> {
                _errorEmail.value = ERROR_WRONG_EMAIL
            }
            is FirebaseAuthUserCollisionException -> {
                _errorEmail.value = ERROR_SUCH_USER_EXIST
            }
            is FirebaseNetworkException -> {
                _errorNetwork.value = Unit
            }
            else -> {
                _errorEmail.value = ERROR
                _errorPassword.value = ERROR
            }
        }
    }

    fun logIn(inputEmail: String, inputPassword: String) {
        val fieldsValid = validateInputLogIn(inputEmail, inputPassword)
        if (fieldsValid) {
            logInUseCase(inputEmail, inputPassword).addOnCompleteListener {
                if (it.isSuccessful) {
                    _userName.value = inputEmail
                } else {
                    Log.i("muri", it.toString())
                    checkLogInException(it)
                }
            }
        }
    }

    fun signOut() {
        val auth = getFirebaseAuthUseCase()
        auth.signOut()
    }

    private fun checkLogInException(it: Task<AuthResult>) {
        when (it.exception) {
            is FirebaseAuthInvalidCredentialsException -> {
                _errorPassword.value = ERROR_WRONG_PASSWORD
            }
            is FirebaseAuthInvalidUserException -> {
                _errorEmail.value = ERROR_NOT_EXISTING_EMAIL
            }
            is FirebaseAuthUserCollisionException -> {
                _errorEmail.value = ERROR_SUCH_USER_EXIST
            }
            is FirebaseNetworkException -> {
                _errorNetwork.value = Unit
            }
            else -> {
                _errorEmail.value = ERROR
                _errorPassword.value = ERROR
            }
        }
    }

    private fun validateInputSignIn(email: String, password: String, flatNum: String): Boolean {
        var result = true
        if (email.isBlank()) {
            _errorEmail.value = ERROR_EMPTY
            result = false
        }
        if (password.length < 6) {
            _errorPassword.value = ERROR_SHORT_PASSWORD
            result = false
        }
        if (password.isBlank()) {
            _errorPassword.value = ERROR_EMPTY
            result = false
        }
        if (flatNum.isBlank()) {
            _errorFlatNum.value = ERROR_EMPTY
            result = false
        } else if (flatNum.toInt() !in 1..60) {
            _errorFlatNum.value = ERROR_NUM_FLAT
            result = false
        }
        return result
    }

    private fun validateInputLogIn(email: String, password: String): Boolean {
        var result = true
        if (email.isBlank()) {
            _errorEmail.value = ERROR_EMPTY
            result = false
        }
        if (password.length < 6) {
            _errorPassword.value = ERROR_SHORT_PASSWORD
            result = false
        }
        if (password.isBlank()) {
            _errorPassword.value = ERROR_EMPTY
            result = false
        }
        return result
    }

    fun resetErrorInputEmail() {
        _errorEmail.value = NO_ERRORS
    }

    fun resetErrorInputPassword() {
        _errorPassword.value = NO_ERRORS
    }

    fun resetErrorInputFlatNum() {
        _errorFlatNum.value = NO_ERRORS
    }


    companion object {
        const val ERROR_EMPTY = "empty"
        const val ERROR_SUCH_USER_EXIST = "email_exist"
        const val ERROR = "error"
        const val NO_ERRORS = "null"
        const val ERROR_SHORT_PASSWORD = "short_password"
        const val ERROR_WRONG_EMAIL = "wrong_email"
        const val ERROR_NOT_EXISTING_EMAIL = "not_existing_email"
        const val ERROR_WRONG_PASSWORD = "wrong_password"
        const val ERROR_NUM_FLAT = "num_flat"
    }
}