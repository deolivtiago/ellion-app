package com.clarxlabs.ellion.auth.domain.services

import com.clarxlabs.ellion.application.utilities.Either
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface AuthenticationService {
    suspend fun signIn(input: SignIn.Input): Either<SignIn.Output, SignIn.Error>
    suspend fun signUp(input: SignUp.Input): Either<SignUp.Output, SignUp.Error>

    interface SignIn {
        @Serializable
        data class Input(
            val email: String,
            val password: String,
        )

        @Serializable
        data class Output(
            @SerialName("access_token")
            val accessToken: String,
            @SerialName("refresh_token")
            val refreshToken: String,
        )

        @Serializable
        data class Error(
            val email: String = "",
            val password: String = "",
        )
    }

    interface SignUp {
        @Serializable
        data class Input(
            val email: String,
            val password: String,
            @SerialName("first_name")
            val firstName: String,
            @SerialName("last_name")
            val lastName: String = "",
            val role: String = "user",
        )

        @Serializable
        data class Output(
            val id: String,
            val email: String,
            @SerialName("first_name")
            val firstName: String,
            @SerialName("last_name")
            val lastName: String = "",
            @SerialName("is_verified")
            val isVerified: Boolean = false,
            val role: String = "user",
        )

        @Serializable
        data class Error(
            val id: String = "",
            val email: String = "",
            val password: String = "",
            @SerialName("first_name")
            val firstName: String = "",
            @SerialName("last_name")
            val lastName: String = "",
            val role: String = "",
        )
    }
}
