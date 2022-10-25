package com.example.homehelper.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.homehelper.domain.usecases.GetFirebaseAuthUseCase
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val getFirebaseAuthUseCase: GetFirebaseAuthUseCase,
) : ViewModel() {

    private var _errorEmail = MutableLiveData<String>()
    val errorEmail: LiveData<String>
        get() = _errorEmail
    private var _errorPassword = MutableLiveData<String>()
    val errorPassword: LiveData<String>
        get() = _errorPassword

    fun signIn(inputEmail: String, inputPassword: String) {
        val auth = getFirebaseAuthUseCase()

        val email = inputEmail.trim()
        val password = inputPassword.trim()
        val fieldsValid = validateInput(email, password)
        if (fieldsValid) {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.i("muri", "successful: $it")
                } else {
                    Log.i("muri", "fail: $it")
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
            else -> {
                _errorEmail.value = ERROR
                _errorPassword.value = ERROR
            }
        }
    }

    fun logIn(inputEmail: String, inputPassword: String) {
        val auth = getFirebaseAuthUseCase()

        val email = inputEmail.trim()
        val password = inputPassword.trim()
        val fieldsValid = validateInput(email, password)
        if (fieldsValid) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.i("muri", it.toString())
                } else {
                    Log.i("muri", it.toString())
                    checkLogInException(it)
                }
            }
        }
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
            else -> {
                _errorEmail.value = ERROR
                _errorPassword.value = ERROR
            }
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        var result = true
        if (email.isEmpty()) {
            _errorEmail.value = ERROR_EMPTY
            result = false
        }
        if (password.length < 7) {
            _errorPassword.value = ERROR_SHORT_PASSWORD
            result = false
        }
        if (password.isEmpty()) {
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


    companion object {
        const val ERROR_EMPTY = "empty"
        const val ERROR_SUCH_USER_EXIST = "email_exist"
        const val ERROR = "error"
        const val NO_ERRORS = "null"
        const val ERROR_SHORT_PASSWORD = "short_password"
        const val ERROR_WRONG_EMAIL = "wrong_email"
        const val ERROR_NOT_EXISTING_EMAIL = "not_existing_email"
        const val ERROR_WRONG_PASSWORD = "wrong_password"
    }
}