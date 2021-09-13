package com.fractaldev.solidexample.utils

class Decorator<T>(
    private val networkSource: T,
    private val databaseSource: T
) {

    suspend fun <R> combine(operation: suspend T.() -> Result<R>): Result<R> {
        return when(val networkResult = networkSource.operation()) {
            is Result.Success -> databaseSource.operation()
            is Result.Error -> networkResult
        }
    }

    suspend fun <R> atLeastOne(operation: suspend T.() -> Result<R>): Result<R> {
        return when(val networkResult = networkSource.operation()) {
            is Result.Success -> networkResult
            is Result.Error -> databaseSource.operation()
        }
    }

}