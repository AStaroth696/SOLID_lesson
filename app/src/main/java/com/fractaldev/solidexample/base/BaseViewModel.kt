package com.fractaldev.solidexample.base

import androidx.lifecycle.ViewModel
import com.fractaldev.solidexample.utils.Result

abstract class BaseViewModel : ViewModel() {

    fun <T> Result<T>.wrap(onSuccess: ((T) -> Unit)? = null, onError: ((Throwable) -> Unit)? = null) {
        when(this) {
            is Result.Success -> onSuccess?.invoke(data)
            is Result.Error -> onError?.invoke(error)
        }
    }

}