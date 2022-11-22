package com.example.homehelper.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.homehelper.domain.usecases.users.GetAllUsersUseCase
import javax.inject.Inject

class UsersListViewModel @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase
): ViewModel() {

    fun getAllUsers() = getAllUsersUseCase.invoke()
}