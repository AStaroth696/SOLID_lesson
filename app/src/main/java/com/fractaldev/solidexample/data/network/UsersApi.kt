package com.fractaldev.solidexample.data.network

import com.fractaldev.solidexample.data.network.model.BaseResponse
import com.fractaldev.solidexample.data.network.model.User
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UsersApi {

    @POST("users")
    suspend fun createUser(@Body user: User): BaseResponse<String>

    @GET("users/{id}")
    suspend fun getUserInfo(@Path("id") id: String): BaseResponse<User>

    @GET("users")
    suspend fun getAllUsers(): BaseResponse<List<User>>

    @POST("users/{id}")
    suspend fun updateUser(@Path("id") id: String, @Body user: User): BaseResponse<User>

    @DELETE("users/{id")
    suspend fun deleteUser(@Path("id") id: String): BaseResponse<String>

}