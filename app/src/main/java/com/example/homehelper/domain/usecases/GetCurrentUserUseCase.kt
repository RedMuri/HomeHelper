package com.example.homehelper.domain.usecases

import com.example.homehelper.domain.FirebaseRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repository: FirebaseRepository
) {

    operator fun invoke(email: String) = repository.getCurrentUser(email)
}