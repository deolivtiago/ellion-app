package com.clarxlabs.ellion.auth.data.remote

import com.clarxlabs.ellion.application.config.APIRoute
import com.clarxlabs.ellion.application.factories.HttpRequestFactory
import com.clarxlabs.ellion.application.utilities.Result
import com.clarxlabs.ellion.application.utilities.ResultData
import com.clarxlabs.ellion.application.utilities.ResultError
import com.clarxlabs.ellion.auth.data.remote.dtos.CredentialsData
import com.clarxlabs.ellion.auth.data.remote.dtos.CredentialsError
import com.clarxlabs.ellion.auth.data.remote.dtos.TokensData
import com.clarxlabs.ellion.auth.data.remote.dtos.UserData
import com.clarxlabs.ellion.auth.data.remote.dtos.UserDataError
import com.clarxlabs.ellion.auth.data.remote.inputs.ConfirmInput
import com.clarxlabs.ellion.auth.data.remote.inputs.SignOutInput
import com.clarxlabs.ellion.auth.data.remote.inputs.VerifyInput
import com.clarxlabs.ellion.auth.domain.entities.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod

class MainAuthDataSource(private val httpClient: HttpClient) : AuthDataSource {
    override suspend fun signIn(input: CredentialsData): Result<TokensData, CredentialsError> =
        HttpRequestFactory(httpClient)
            .create(HttpMethod.Post, APIRoute.SIGNIN)
            .setBody(input)
            .execute()
            .let { mapResult(it) }


    override suspend fun signUp(input: UserData): Result<User, UserDataError> =
        HttpRequestFactory(httpClient)
            .create(HttpMethod.Post, APIRoute.SIGNUP)
            .setBody(input)
            .execute()
            .let { mapResult(it) }

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

    override suspend fun listUsers(): HttpResponse =
        HttpRequestFactory(httpClient)
            .create(HttpMethod.Get, APIRoute.LISTUSERS)
            .execute()

    private suspend inline fun <reified D : ResultData, reified E : ResultError> mapResult(it: HttpResponse): Result<D, E> {
        return when (it.status.value) {
            in 200..201 -> {
                it.body<Result.Data<D, E>>()
            }

            in 422..422 -> {
                it.body<Result.Error<D, E>>()
            }

            else -> throw NotImplementedError()
        }
    }
}
