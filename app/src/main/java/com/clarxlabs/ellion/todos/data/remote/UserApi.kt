package com.clarxlabs.ellion.todos.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JsonData<T>(
    @SerialName("data")
    val data: T
)

interface IUserApiService {
    suspend fun listUsers(): JsonData<List<UserModel>>
}

class UserApiService(private val httpClient: HttpClient = ApiClient.client) : IUserApiService {
    override suspend fun listUsers(): JsonData<List<UserModel>> {
        return httpClient.get("/api/users").body()
    }
}