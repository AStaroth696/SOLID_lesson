package com.fractaldev.solidexample.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fractaldev.solidexample.base.BaseViewModel
import com.fractaldev.solidexample.domain.model.User
import com.fractaldev.solidexample.domain.repository.UserRepository
import com.fractaldev.solidexample.utils.UserIdGenerator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersListViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val idGenerator: UserIdGenerator
) : BaseViewModel() {

    private val mutableUsers = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = mutableUsers
    private val mutableError = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = mutableError

    fun getAllUsers() {
        viewModelScope.launch {
            userRepository.getAllUsers().wrap(mutableUsers::postValue, mutableError::postValue)
        }
    }

    fun createUser(
        firstName: String,
        lastName: String,
        dateOfBirth: String,
        address: String
    ) {
        viewModelScope.launch {
            val user = User(
                idGenerator.generateUserId(),
                firstName,
                lastName,
                dateOfBirth,
                address
            )
            userRepository.createUser(user).wrap(
                onSuccess = { getAllUsers() },
                mutableError::postValue
            )
        }
    }

}