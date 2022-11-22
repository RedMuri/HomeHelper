package com.example.homehelper.data.mappers

import com.example.homehelper.data.firebase.model.UserDto
import com.example.homehelper.domain.entities.User
import javax.inject.Inject

class UsersMapper @Inject constructor() {

    fun userDtoToEntity(userDto: UserDto) = User(
        email = userDto.email,
    )
}