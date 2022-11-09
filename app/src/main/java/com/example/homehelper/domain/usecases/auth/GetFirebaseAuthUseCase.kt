package com.example.homehelper.domain.usecases.auth

import com.example.homehelper.domain.FirebaseRepository
import com.example.homehelper.domain.usecases.repositories.FirebaseAuthRepository
import javax.inject.Inject

class GetFirebaseAuthUseCase @Inject constructor(
    private val repository: FirebaseAuthRepository
) {

    operator fun invoke() = repository.getFirebaseAuth()
}