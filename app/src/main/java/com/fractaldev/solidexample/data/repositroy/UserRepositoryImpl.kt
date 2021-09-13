package com.fractaldev.solidexample.data.repositroy

import com.fractaldev.solidexample.di.Database
import com.fractaldev.solidexample.di.Network
import com.fractaldev.solidexample.domain.model.User
import com.fractaldev.solidexample.domain.repository.UserRepository
import com.fractaldev.solidexample.utils.Decorator
import com.fractaldev.solidexample.utils.Result
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    @Database
    private val databaseRepository: UserRepository,
    @Network
    private val networkRepository: UserRepository
) : UserRepository {

    private val decorator = Decorator(
        networkSource = networkRepository,
        databaseSource = databaseRepository
    )

    override suspend fun createUser(user: User): Result<Unit> {
        return decorator.combine { createUser(user) }
    }

    override suspend fun getUserById(id: String): Result<User> {
        return decorator.atLeastOne { getUserById(id) }
    }

    override suspend fun getAllUsers(): Result<List<User>> {
        return when (val networkUsers = networkRepository.getAllUsers()) {
            is Result.Success -> {
                networkUsers.data.forEach {
                    databaseRepository.createUser(it)
                }
                networkUsers
            }
            is Result.Error -> {
                databaseRepository.getAllUsers()
            }
        }
    }

    override suspend fun updateUser(user: User): Result<Unit> {
        return decorator.combine { updateUser(user) }
    }

    override suspend fun deleteUserById(id: String): Result<Unit> {
        return decorator.combine { deleteUserById(id) }
    }

}