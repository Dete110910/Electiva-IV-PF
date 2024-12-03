package com.example.electivaiv.domain.model

data class User(
    val name: String,
    val lastName: String,
    val email: String,
    val password: String,
    val profilePhoto: String,
    var uid: String = ""
)