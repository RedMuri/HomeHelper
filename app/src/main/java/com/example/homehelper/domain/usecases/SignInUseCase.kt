package com.example.homehelper.domain.usecases

import com.example.homehelper.domain.FirebaseRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: FirebaseRepository
) {

    operator fun invoke(email: String, password: String, flatNum: Int) =
        repository.signIn(email,password,flatNum)
}