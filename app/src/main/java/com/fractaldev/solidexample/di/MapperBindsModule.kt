package com.fractaldev.solidexample.di

import com.fractaldev.solidexample.utils.SafeUsersMapper
import com.fractaldev.solidexample.utils.UserMapper
import com.fractaldev.solidexample.utils.UserMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MapperBindsModule {

    @Binds
    abstract fun bindUserMapper(impl: UserMapperImpl): UserMapper

    @Binds
    @SafeMapper
    abstract fun bindSafeUserMapper(impl: SafeUsersMapper): UserMapper

}