package com.clarxlabs.ellion.auth.data.remote

import com.clarxlabs.ellion.application.config.RequestOptions
import com.clarxlabs.ellion.application.config.makeRequest
import com.clarxlabs.ellion.auth.data.remote.dtos.SignInInput
import com.clarxlabs.ellion.auth.data.remote.dtos.SignUpInput
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse

class MainAuthDataSource(private val httpClient: HttpClient) : AuthDataSource {
    override suspend fun signIn(
        signInInput: SignInInput, signInRequest: RequestOptions
    ): HttpResponse = httpClient
        .makeRequest(signInRequest) { setBody(signInInput) }

    override suspend fun signUp(
        signUpInput: SignUpInput, signUpRequest: RequestOptions
    ): HttpResponse = httpClient
        .makeRequest(signUpRequest) { setBody(signUpInput) }
}
