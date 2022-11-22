package com.example.homehelper.data.firebase.model

data class UserDto(
    var id: String = "none",
    var surname: String = "none",
    var name: String = "none",
    var patronymic: String = "none",
    var gender: String = "none",
    var age: Int = 0,
    var passport: String = "none",
    var email: String = "none",
    var city: String = "none",
    var street: String = "none",
    var house: Int = 0,
    var flat: Int = 0,
    var login: String = "none",
    var password: String = "none"
)
