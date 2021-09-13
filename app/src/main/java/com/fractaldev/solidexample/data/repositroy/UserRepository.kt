package com.fractaldev.solidexample.data.repositroy

import android.content.Context
import androidx.room.Room
import com.fractaldev.solidexample.data.databse.UsersDatabase
import com.fractaldev.solidexample.data.network.UsersApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.fractaldev.solidexample.data.databse.model.User as DatabaseUser
import com.fractaldev.solidexample.data.network.model.User as NetworkUser

class UserRepository(context: Context) {

    private val userApi = Retrofit.Builder()
        .baseUrl("https://userap.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(UsersApi::class.java)
    private val userDatabase = Room.databaseBuilder(
        context,
        UsersDatabase::class.java,
        "users.db"
    ).build()
    private val dao = userDatabase.userDao()

    suspend fun createNetworkUser(user: NetworkUser) {
        userApi.createUser(user)
    }

    suspend fun createDatabaseUser(user: DatabaseUser) {
        dao.insert(user)
    }

    suspend fun getNetworkUserById(id: String): NetworkUser {
        return userApi.getUserInfo(id).data!!
    }

    suspend fun getDatabaseUserById(id: String): DatabaseUser {
        return dao.getById(id)
    }

    suspend fun getAllNetworkUsers(): List<NetworkUser> {
        return userApi.getAllUsers().data!!
    }

    suspend fun getAllDatabaseUsers(): List<DatabaseUser> {
        return dao.getAll()
    }

    suspend fun updateNetworkUser(user: NetworkUser) {
        userApi.updateUser(user.id!!, user)
    }

    suspend fun updateDatabaseUser(user: DatabaseUser) {
        dao.update(user)
    }

    suspend fun deleteNetworkUserById(id: String) {
        userApi.deleteUser(id)
    }

    suspend fun deleteDatabaseUserById(id: String) {
        dao.deleteById(id)
    }

}