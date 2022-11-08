package com.example.homehelper.domain.usecases.auth

import com.example.homehelper.domain.FirebaseRepository
import javax.inject.Inject

class LogInUseCase @Inject constructor(
    private val repository: FirebaseRepository
) {

    operator fun invoke(email: String, password: String) =
        repository.logIn(email,password)
}