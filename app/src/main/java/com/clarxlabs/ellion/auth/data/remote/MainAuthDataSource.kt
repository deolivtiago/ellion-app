package com.clarxlabs.ellion.auth.data.remote

import com.clarxlabs.ellion.application.config.RequestOptions
import com.clarxlabs.ellion.application.config.makeRequest
import com.clarxlabs.ellion.auth.data.remote.dtos.ConfirmInput
import com.clarxlabs.ellion.auth.data.remote.dtos.SignInInput
import com.clarxlabs.ellion.auth.data.remote.dtos.SignOutInput
import com.clarxlabs.ellion.auth.data.remote.dtos.SignUpInput
import com.clarxlabs.ellion.auth.data.remote.dtos.VerifyInput
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse

class MainAuthDataSource(private val httpClient: HttpClient) : AuthDataSource {
    override suspend fun signIn(
        input: SignInInput, requestOptions: RequestOptions
    ): HttpResponse = makeRequest(input, requestOptions)

    override suspend fun signUp(
        input: SignUpInput, requestOptions: RequestOptions
    ): HttpResponse = makeRequest(input, requestOptions)

    override suspend fun signOut(
        input: SignOutInput, requestOptions: RequestOptions
    ): HttpResponse {
        val queries = mapOf(
            "access_token" to input.accessToken,
            "refresh_token" to input.refreshToken,
        )

        return makeRequest(requestOptions.copy(queries = queries))
    }

    override suspend fun verify(
        input: VerifyInput, requestOptions: RequestOptions
    ): HttpResponse {
        val queries = mapOf("email" to input.email)

        return makeRequest(requestOptions.copy(queries = queries))
    }

    override suspend fun confirm(
        input: ConfirmInput, requestOptions: RequestOptions
    ): HttpResponse {
        val queries = mapOf("email" to input.email, "code" to input.code)

        return makeRequest(requestOptions.copy(queries = queries))
    }

    private suspend inline fun <reified T> makeRequest(
        body: T, requestOptions: RequestOptions
    ): HttpResponse = httpClient.makeRequest(requestOptions) { setBody(body) }

    private suspend fun makeRequest(
        requestOptions: RequestOptions
    ): HttpResponse = httpClient.makeRequest(requestOptions)
}
