package com.clarxlabs.ellion.auth.data.remote

import com.clarxlabs.ellion.application.config.APIRoute
import com.clarxlabs.ellion.application.factories.HttpRequestFactory
import com.clarxlabs.ellion.auth.data.remote.inputs.ConfirmInput
import com.clarxlabs.ellion.auth.data.remote.inputs.SignInInput
import com.clarxlabs.ellion.auth.data.remote.inputs.SignOutInput
import com.clarxlabs.ellion.auth.data.remote.inputs.SignUpInput
import com.clarxlabs.ellion.auth.data.remote.inputs.VerifyInput
import io.ktor.client.HttpClient
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod

class MainAuthDataSource(private val httpClient: HttpClient) : AuthDataSource {
    override suspend fun signIn(input: SignInInput): HttpResponse =
        HttpRequestFactory(httpClient)
            .create(HttpMethod.Post, APIRoute.SIGNIN)
            .setBody(input)
            .execute()

    override suspend fun signUp(input: SignUpInput): HttpResponse =
        HttpRequestFactory(httpClient)
            .create(HttpMethod.Post, APIRoute.SIGNUP)
            .setBody(input)
            .execute()

    override suspend fun signOut(input: SignOutInput): HttpResponse {
        val queries = mapOf(
            "access_token" to input.accessToken,
            "refresh_token" to input.refreshToken,
        )

        return HttpRequestFactory(httpClient)
            .create(HttpMethod.Delete, APIRoute.SIGNOUT)
            .setQueries(queries)
            .execute()
    }

    override suspend fun verify(input: VerifyInput): HttpResponse =
        HttpRequestFactory(httpClient)
            .create(HttpMethod.Get, APIRoute.VERIFY)
            .setQueries(mapOf("email" to input.email))
            .execute()

    override suspend fun confirm(input: ConfirmInput): HttpResponse =
        HttpRequestFactory(httpClient)
            .create(HttpMethod.Post, APIRoute.CONFIRM)
            .setQueries(mapOf("email" to input.email, "code" to input.code))
            .execute()
}
