package com.fractaldev.solidexample.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fractaldev.solidexample.data.repositroy.UserRepository
import kotlinx.coroutines.launch
import java.lang.Exception
import com.fractaldev.solidexample.data.databse.model.User as DatabaseUser
import com.fractaldev.solidexample.data.network.model.User as NetworkUser

class UserViewModel(context: Context) : ViewModel() {

    private val userRepository = UserRepository(context)
    val user = MutableLiveData<DatabaseUser>()
    val error = MutableLiveData<Throwable>()

    fun getUserById(id: String) {
        viewModelScope.launch {
            val user = try {
                val networkUser = userRepository.getNetworkUserById(id)
                val databaseUser = DatabaseUser(
                    networkUser.id!!,
                    networkUser.firstName!!,
                    networkUser.lastName!!,
                    networkUser.dateOfBirth!!,
                    networkUser.address!!
                )
                databaseUser
            } catch (e: Exception) {
                error.postValue(e)
                userRepository.getDatabaseUserById(id)
            }
            this@UserViewModel.user.postValue(user)
        }
    }

    fun updateUser(updatedUser: DatabaseUser) {
        viewModelScope.launch {
            try {
                val networkUser = NetworkUser(
                    updatedUser.id,
                    updatedUser.firstName,
                    updatedUser.lastName,
                    updatedUser.dateOfBirth,
                    updatedUser.address
                )
                userRepository.updateNetworkUser(networkUser)
                userRepository.updateDatabaseUser(updatedUser)
            } catch (e: Exception) {
                error.postValue(e)
            }
        }
    }

    fun deleteUser(user: DatabaseUser) {
        viewModelScope.launch {
            try {
                userRepository.deleteNetworkUserById(user.id)
                userRepository.deleteDatabaseUserById(user.id)
            } catch (e: Exception) {
                error.postValue(e)
            }
        }
    }

}