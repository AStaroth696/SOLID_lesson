package com.fractaldev.solidexample.di

import android.content.Context
import androidx.room.Room
import com.fractaldev.solidexample.data.databse.UsersDatabase
import com.fractaldev.solidexample.data.databse.dao.UserDao
import com.fractaldev.solidexample.data.network.UsersApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }
    @Provides
    fun provideRetrofit(converterFactory: Converter.Factory): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://userap.com/")
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    fun provideUsersApi(retrofit: Retrofit): UsersApi {
        return retrofit.create(UsersApi::class.java)
    }

    @Provides
    fun provideUsersDatabase(@ApplicationContext context: Context): UsersDatabase {
        return Room.databaseBuilder(
            context,
            UsersDatabase::class.java,
            "users.db"
        ).build()
    }

    @Provides
    fun provideUserDao(usersDatabase: UsersDatabase): UserDao {
        return usersDatabase.userDao()
    }

}