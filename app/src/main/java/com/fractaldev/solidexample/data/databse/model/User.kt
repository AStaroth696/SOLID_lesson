package com.fractaldev.solidexample.data.databse.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: String,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: String,
    val address: String
)
