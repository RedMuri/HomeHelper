package com.example.homehelper.domain.repositories

import androidx.lifecycle.LiveData
import com.example.homehelper.domain.entities.User

interface FirebaseUsersRepository {

    fun getAllUsers(): LiveData<List<User>>
}