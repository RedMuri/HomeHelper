package com.example.homehelper.domain.usecases

import com.example.homehelper.domain.FirebaseRepository
import javax.inject.Inject

class GetFirebaseAuthUseCase @Inject constructor(
    private val repository: FirebaseRepository
) {

    operator fun invoke() = repository.getFirebaseAuth()
}