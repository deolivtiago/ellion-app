package com.clarxlabs.ellion.auth.signin.data


import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

private const val BASE_URL = "https://ellion.gigalixirapp.com"

class RemoteDataSource(private val httpClient: HttpClient) {

    suspend fun signIn(): HttpResponse {
        return httpClient.get(urlString = "$BASE_URL/api/users")
    }
}
