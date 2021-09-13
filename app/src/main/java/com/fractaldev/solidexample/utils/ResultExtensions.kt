package com.fractaldev.solidexample.utils

import java.lang.Exception

suspend fun <T> wrapSafe(operation: suspend () -> T): Result<T> {
    return try {
        Result.Success(operation())
    } catch (e: Exception) {
        Result.Error(e)
    }
}