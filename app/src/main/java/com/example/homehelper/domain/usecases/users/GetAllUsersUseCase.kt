package com.example.homehelper.domain.usecases.users

import com.example.homehelper.domain.repositories.FirebaseUsersRepository
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    private val repository: FirebaseUsersRepository
) {

    operator fun invoke() = repository.getAllUsers()
}