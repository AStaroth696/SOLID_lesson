package com.fractaldev.solidexample.data.databse.repository

import com.fractaldev.solidexample.data.databse.dao.UserDao
import com.fractaldev.solidexample.domain.model.User
import com.fractaldev.solidexample.domain.repository.UserRepository
import com.fractaldev.solidexample.utils.Result
import com.fractaldev.solidexample.utils.UserMapper
import com.fractaldev.solidexample.utils.wrapSafe
import javax.inject.Inject

class UserDatabaseRepository @Inject constructor(
    private val dao: UserDao,
    private val mapper: UserMapper
) : UserRepository {

    override suspend fun createUser(user: User): Result<Unit> {
        return wrapSafe {
            dao.insert(mapper.domainToDatabase(user))
        }
    }

    override suspend fun getUserById(id: String): Result<User> {
        return wrapSafe {
            mapper.databaseToDomain(dao.getById(id))
        }
    }

    override suspend fun getAllUsers(): Result<List<User>> {
        return wrapSafe {
            dao.getAll().map(mapper::databaseToDomain)
        }
    }

    override suspend fun updateUser(user: User): Result<Unit> {
        return wrapSafe {
            dao.update(mapper.domainToDatabase(user))
        }
    }

    override suspend fun deleteUserById(id: String): Result<Unit> {
        return wrapSafe {
            dao.deleteById(id)
        }
    }

}