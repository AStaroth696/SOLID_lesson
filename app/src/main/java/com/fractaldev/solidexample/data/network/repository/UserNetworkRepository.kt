package com.fractaldev.solidexample.data.network.repository

import com.fractaldev.solidexample.data.network.UsersApi
import com.fractaldev.solidexample.data.network.model.BaseResponse
import com.fractaldev.solidexample.di.SafeMapper
import com.fractaldev.solidexample.domain.model.User
import com.fractaldev.solidexample.domain.repository.UserRepository
import com.fractaldev.solidexample.utils.Result
import com.fractaldev.solidexample.utils.UserMapper
import com.fractaldev.solidexample.utils.wrapSafe
import javax.inject.Inject

class UserNetworkRepository @Inject constructor(
    private val api: UsersApi,
    @SafeMapper
    private val mapper: UserMapper
) : UserRepository {

    override suspend fun createUser(user: User): Result<Unit> {
        return wrapSafe {
            api.createUser(mapper.domainToNetwork(user)).wrapResponse {}
        }
    }

    override suspend fun getUserById(id: String): Result<User> {
        return wrapSafe {
            api.getUserInfo(id).wrapResponse { mapper.networkToDomain(it) }
        }
    }

    override suspend fun getAllUsers(): Result<List<User>> {
        return wrapSafe {
            api.getAllUsers().wrapResponse {
                it.map(mapper::networkToDomain)
            }
        }
    }

    override suspend fun updateUser(user: User): Result<Unit> {
        return wrapSafe {
            api.updateUser(user.id, mapper.domainToNetwork(user)).wrapResponse {}
        }
    }

    override suspend fun deleteUserById(id: String): Result<Unit> {
        return wrapSafe {
            api.deleteUser(id).wrapResponse {}
        }
    }

    private fun <T, R> BaseResponse<T>.wrapResponse(onSuccess: (T) -> R): R {
        return if (error == null) {
            data?.let {
                onSuccess(it)
            } ?: throw IllegalStateException("No response data")
        } else {
            throw IllegalStateException(error)
        }
    }

}