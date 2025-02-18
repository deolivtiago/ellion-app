package com.clarxlabs.ellion.auth.data.remote

import com.clarxlabs.ellion.application.config.MainAPIRoute
import com.clarxlabs.ellion.application.config.RequestOptions
import com.clarxlabs.ellion.auth.data.remote.dtos.*
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface AuthDataSource {
    suspend fun signUp(
        input: SignUpInput,
        requestOptions: RequestOptions = RequestOptions(MainAPIRoute.SIGNUP, HttpMethod.Post)
    ): HttpResponse

    suspend fun signIn(
        input: SignInInput,
        requestOptions: RequestOptions = RequestOptions(MainAPIRoute.SIGNIN, HttpMethod.Post)
    ): HttpResponse

    suspend fun signOut(
        input: SignOutInput,
        requestOptions: RequestOptions = RequestOptions(MainAPIRoute.SIGNOUT, HttpMethod.Delete)
    ): HttpResponse

    suspend fun verify(
        input: VerifyInput,
        requestOptions: RequestOptions = RequestOptions(MainAPIRoute.VERIFY, HttpMethod.Get)
    ): HttpResponse

    suspend fun confirm(
        input: ConfirmInput,
        requestOptions: RequestOptions = RequestOptions(MainAPIRoute.CONFIRM, HttpMethod.Post)
    ): HttpResponse
}

@Serializable
data class Auth(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("refresh_token")
    val refreshToken: String,
)
