package com.fractaldev.solidexample.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fractaldev.solidexample.base.BaseViewModel
import com.fractaldev.solidexample.domain.model.User
import com.fractaldev.solidexample.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val mutableUser = MutableLiveData<User>()
    val user: LiveData<User> = mutableUser
    private val mutableError = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = mutableError

    fun getUserById(id: String) {
        viewModelScope.launch {
            userRepository.getUserById(id).wrap(mutableUser::postValue, mutableError::postValue)
        }
    }

    fun updateUser(updatedUser: User) {
        viewModelScope.launch {
            userRepository.updateUser(updatedUser).wrap(
                onSuccess = { getUserById(updatedUser.id) },
                mutableError::postValue
            )
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            userRepository.deleteUserById(user.id).wrap(onError = mutableError::postValue)
        }
    }

}