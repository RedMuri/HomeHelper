package com.example.homehelper.domain.entities

data class User(
    var id: String? = null,
    var surname: String? = null,
    var name: String? = null,
    var patronymic: String? = null,
    var gender: String? = null,
    var age: Int? = null,
    var passport: String? = null,
    var email: String? = null,
    var city: String? = null,
    var street: String? = null,
    var house: Int? = null,
    var flat: Int? = null,
    var login: String? = null,
    var password: String? = null
)

