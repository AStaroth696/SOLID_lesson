package com.fractaldev.solidexample.domain.model

data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: String,
    val address: String
)