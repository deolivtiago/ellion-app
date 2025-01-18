package com.clarxlabs.ellion.todos.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

@Serializable
data class JsonData<T>(
    @SerialName("data")
    val data: T
)

interface UserApi {

    @GET("users")
    suspend fun listUsers(): Response<JsonData<List<UserModel>>>

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): Response<JsonData<UserModel>>
}
