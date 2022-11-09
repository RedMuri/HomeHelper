package com.example.homehelper.domain.usecases.auth

import com.example.homehelper.domain.repositories.FirebaseAuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: FirebaseAuthRepository
) {

    operator fun invoke(email: String, password: String, flatNum: Int) =
        repository.signIn(email,password,flatNum)
}