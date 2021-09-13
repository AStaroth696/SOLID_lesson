package com.fractaldev.solidexample.utils

import javax.inject.Inject
import com.fractaldev.solidexample.data.network.model.User as NetworkUser
import com.fractaldev.solidexample.data.databse.model.User as DatabaseUser
import com.fractaldev.solidexample.domain.model.User as DomainUser

interface UserMapper {
    
    fun databaseToDomain(user: DatabaseUser): DomainUser
    fun domainToDatabase(user: DomainUser): DatabaseUser
    fun networkToDomain(user: NetworkUser): DomainUser
    fun domainToNetwork(user: DomainUser): NetworkUser
    
}

open class UserMapperImpl @Inject constructor() : UserMapper {

    override fun databaseToDomain(user: DatabaseUser): DomainUser {
        return DomainUser(
            id = user.id,
            firstName = user.firstName,
            lastName = user.lastName,
            dateOfBirth = user.dateOfBirth,
            address = user.address
        )
    }

    override fun domainToDatabase(user: DomainUser): DatabaseUser {
        return DatabaseUser(
            id = user.id,
            firstName = user.firstName,
            lastName = user.lastName,
            dateOfBirth = user.dateOfBirth,
            address = user.address
        )
    }

    override fun networkToDomain(user: NetworkUser): DomainUser {
        return DomainUser(
            id = user.id ?: throw IllegalArgumentException("User id is null"),
            firstName = user.firstName ?: throw IllegalArgumentException("User firstName is null"),
            lastName = user.lastName ?: throw IllegalArgumentException("User id lastName null"),
            dateOfBirth = user.dateOfBirth ?: throw IllegalArgumentException("User dataOfBirth is null"),
            address = user.address ?: throw IllegalArgumentException("User id address null"),
        )
    }

    override fun domainToNetwork(user: DomainUser): NetworkUser {
        return NetworkUser(
            id = user.id,
            firstName = user.firstName,
            lastName = user.lastName,
            dateOfBirth = user.dateOfBirth,
            address = user.address
        )
    }

}

class SafeUsersMapper : UserMapperImpl() {

    override fun networkToDomain(user: NetworkUser): DomainUser {
        return DomainUser(
            id = user.id ?: "",
            firstName = user.firstName ?: "",
            lastName = user.lastName ?: "",
            dateOfBirth = user.dateOfBirth ?: "",
            address = user.address ?: "",
        )
    }

}