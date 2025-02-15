package com.clarxlabs.ellion.application.defaults

import com.clarxlabs.ellion.application.config.RequestOptions
import com.clarxlabs.ellion.application.config.makeRequest
import io.ktor.client.HttpClient
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class DefaultAuthDataSource(private val httpClient: HttpClient) {

    suspend fun listUsers(
        listUsersRequest: RequestOptions = RequestOptions("/api/users", HttpMethod.Get)
    ): HttpResponse = httpClient
        .makeRequest(listUsersRequest)
}

@Serializable
data class UserOutput(
    val id: String,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("last_name")
    val lastName: String,
    val email: String,
    val password: String,
    @SerialName("avatar_url")
    val avatarUrl: String,
    @SerialName("is_verified")
    val isVerified: Boolean,
    val role: String,
)


