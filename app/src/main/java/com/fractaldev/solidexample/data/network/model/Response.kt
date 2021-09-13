package com.fractaldev.solidexample.data.network.model

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    val data: T?,
    val error: String?
)

data class User(
    @SerializedName("id")
    val id: String?,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("last_name")
    val lastName: String?,
    @SerializedName("date_of_birth")
    val dateOfBirth: String?,
    @SerializedName("address")
    val address: String?
)