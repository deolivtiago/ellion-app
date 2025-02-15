package com.clarxlabs.ellion.auth.data.remote

import com.clarxlabs.ellion.application.config.MainAPIRoutes
import com.clarxlabs.ellion.application.config.RequestOptions
import com.clarxlabs.ellion.auth.data.remote.dtos.*
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod

interface AuthDataSource {
    suspend fun signUp(
        input: SignUpInput,
        requestOptions: RequestOptions = RequestOptions(MainAPIRoutes.SIGNUP, HttpMethod.Post)
    ): HttpResponse

    suspend fun signIn(
        input: SignInInput,
        requestOptions: RequestOptions = RequestOptions(MainAPIRoutes.SIGNIN, HttpMethod.Post)
    ): HttpResponse
}
