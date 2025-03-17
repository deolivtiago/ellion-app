package com.clarxlabs.ellion.auth.data.remote

import com.clarxlabs.ellion.application.config.APIRoute
import com.clarxlabs.ellion.application.factories.HttpRequestFactory
import com.clarxlabs.ellion.application.utilities.Either
import com.clarxlabs.ellion.auth.data.remote.AuthenticationDataSource.Confirm
import com.clarxlabs.ellion.auth.data.remote.AuthenticationDataSource.ListUsers
import com.clarxlabs.ellion.auth.data.remote.AuthenticationDataSource.SignIn
import com.clarxlabs.ellion.auth.data.remote.AuthenticationDataSource.SignOut
import com.clarxlabs.ellion.auth.data.remote.AuthenticationDataSource.SignUp
import com.clarxlabs.ellion.auth.data.remote.AuthenticationDataSource.Verify
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class AuthenticationDataSourceImpl(private val httpClient: HttpClient) : AuthenticationDataSource {
    override suspend fun signIn(input: SignIn.Input): Either<SignIn.Output, SignIn.Error> =
        HttpRequestFactory(httpClient)
            .create(HttpMethod.Post, APIRoute.SIGNIN)
            .setBody(input)
            .execute()
            .let { mapResponse<SignIn.Output, SignIn.Error>(it) }
            .let {
                when (it) {
                    is Response.Data -> Either.Success(it.data)
                    is Response.Error -> Either.Failure(it.errors)
                }
            }


    override suspend fun signUp(input: SignUp.Input): Either<SignUp.Output, SignUp.Error> =
        HttpRequestFactory(httpClient)
            .create(HttpMethod.Post, APIRoute.SIGNUP)
            .setBody(input)
            .execute()
            .let { mapResponse<SignUp.Output, SignUp.Error>(it) }
            .let {
                when (it) {
                    is Response.Data -> Either.Success(it.data)
                    is Response.Error -> Either.Failure(it.errors)
                }
            }

    override suspend fun signOut(input: SignOut.Input): Either<SignOut.Output, SignOut.Error> {
        val queries = mapOf(
            "access_token" to input.accessToken,
            "refresh_token" to input.refreshToken,
        )

        return HttpRequestFactory(httpClient)
            .create(HttpMethod.Delete, APIRoute.SIGNOUT)
            .setQueries(queries)
            .execute()
            .let { mapResponse<SignOut.Output, SignOut.Error>(it) }
            .let {
                when (it) {
                    is Response.Data -> Either.Success(it.data)
                    is Response.Error -> Either.Failure(it.errors)
                }
            }
    }

    override suspend fun verify(input: Verify.Input): Either<Verify.Output, Verify.Error> =
        HttpRequestFactory(httpClient)
            .create(HttpMethod.Get, APIRoute.VERIFY)
            .setQueries(mapOf("email" to input.email))
            .execute()
            .let { mapResponse<Verify.Output, Verify.Error>(it) }
            .let {
                when (it) {
                    is Response.Data -> Either.Success(it.data)
                    is Response.Error -> Either.Failure(it.errors)
                }
            }

    override suspend fun confirm(input: Confirm.Input): Either<Confirm.Output, Confirm.Error> =
        HttpRequestFactory(httpClient)
            .create(HttpMethod.Post, APIRoute.CONFIRM)
            .setQueries(mapOf("email" to input.email, "code" to input.code))
            .execute()
            .let { mapResponse<Confirm.Output, Confirm.Error>(it) }
            .let {
                when (it) {
                    is Response.Data -> Either.Success(it.data)
                    is Response.Error -> Either.Failure(it.errors)
                }
            }

    override suspend fun listUsers(): Either<ListUsers.Output, ListUsers.Error> =
        HttpRequestFactory(httpClient)
            .create(HttpMethod.Get, APIRoute.LISTUSERS)
            .execute()
            .let { mapResponse<ListUsers.Output, ListUsers.Error>(it) }
            .let {
                when (it) {
                    is Response.Data -> Either.Success(it.data)
                    is Response.Error -> Either.Failure(it.errors)
                }
            }

    private suspend inline fun <reified D, reified E> mapResponse(it: HttpResponse): Response<D, E> {
        return when (it.status.value) {
            in 200..201 -> {
                it.body<Response.Data<D, E>>()
            }

            in 422..422 -> {
                it.body<Response.Error<D, E>>()
            }

            else -> throw NotImplementedError()
        }
    }
}


sealed interface Response<out D, out E> {
    @Serializable
    data class Data<out D, out E>(
        val data: D
    ) : Response<D, E>

    @Serializable
    data class Error<out D, out E>(
        @SerialName("errors") val errors: E
    ) : Response<D, E>
}
