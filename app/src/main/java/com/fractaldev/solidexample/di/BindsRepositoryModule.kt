package com.fractaldev.solidexample.di

import com.fractaldev.solidexample.data.databse.repository.UserDatabaseRepository
import com.fractaldev.solidexample.data.network.repository.UserNetworkRepository
import com.fractaldev.solidexample.data.repositroy.UserRepositoryImpl
import com.fractaldev.solidexample.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsRepositoryModule {

    @Binds
    @Database
    abstract fun bindUserDatabaseRepository(
        userDatabaseRepository: UserDatabaseRepository
    ): UserRepository

    @Binds
    @Network
    abstract fun bindUserNetworkRepository(
        userNetworkRepository: UserNetworkRepository
    ): UserRepository

    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

}