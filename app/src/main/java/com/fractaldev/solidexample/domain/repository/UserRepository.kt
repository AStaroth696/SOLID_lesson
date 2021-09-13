package com.fractaldev.solidexample.domain.repository

import com.fractaldev.solidexample.domain.model.User
import com.fractaldev.solidexample.utils.Result

interface UserRepository {

    suspend fun createUser(user: User): Result<Unit>

    suspend fun getUserById(id: String): Result<User>

    suspend fun getAllUsers(): Result<List<User>>

    suspend fun updateUser(user: User): Result<Unit>

    suspend fun deleteUserById(id: String): Result<Unit>

}