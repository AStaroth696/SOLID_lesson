package com.fractaldev.solidexample.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fractaldev.solidexample.data.repositroy.UserRepository
import kotlinx.coroutines.launch
import java.util.UUID
import com.fractaldev.solidexample.data.databse.model.User as DatabaseUser
import com.fractaldev.solidexample.data.network.model.User as NetworkUser

class UsersListViewModel(context: Context) : ViewModel() {

    private val userRepository = UserRepository(context)
    val users = MutableLiveData<List<DatabaseUser>>()
    val error = MutableLiveData<Throwable>()

    fun getAllUsers() {
        viewModelScope.launch {
            val users = try {
                val networkUsers = userRepository.getAllNetworkUsers()
                networkUsers.forEach { networkUser ->
                    val databaseUser = DatabaseUser(
                        networkUser.id!!,
                        networkUser.firstName!!,
                        networkUser.lastName!!,
                        networkUser.dateOfBirth!!,
                        networkUser.address!!
                    )
                    userRepository.createDatabaseUser(databaseUser)
                }
                networkUsers.map {
                    DatabaseUser(
                        it.id!!,
                        it.firstName!!,
                        it.lastName!!,
                        it.dateOfBirth!!,
                        it.address!!
                    )
                }
            } catch (e: Exception) {
                error.postValue(e)
                userRepository.getAllDatabaseUsers()
            }
            this@UsersListViewModel.users.postValue(users)
        }
    }

    fun createUser(
        firstName: String,
        lastName: String,
        dateOfBirth: String,
        address: String
    ) {
        viewModelScope.launch {
            val networkUser = NetworkUser(
                generateUserId(),
                firstName,
                lastName,
                dateOfBirth,
                address
            )
            try {
                userRepository.createNetworkUser(networkUser)
                val databaseUser = DatabaseUser(
                    networkUser.id!!,
                    networkUser.firstName!!,
                    networkUser.lastName!!,
                    networkUser.dateOfBirth!!,
                    networkUser.address!!
                )
                userRepository.createDatabaseUser(databaseUser)
            } catch (e: Exception) {
                error.postValue(e)
            }

        }
    }

    fun generateUserId(): String {
        return UUID.randomUUID().toString()
    }
}