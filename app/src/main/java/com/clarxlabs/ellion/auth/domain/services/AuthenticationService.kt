package com.clarxlabs.ellion.auth.domain.services

import com.clarxlabs.ellion.application.utilities.Either
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface AuthenticationService {
    suspend fun signIn(input: SignIn.Input): Either<SignIn.Output, SignIn.Error>
    suspend fun signUp(input: SignUp.Input): Either<SignUp.Output, SignUp.Error>
    suspend fun signOut(input: SignOut.Input): Either<SignOut.Output, SignOut.Error>
    suspend fun verify(input: Verify.Input): Either<Verify.Output, Verify.Error>
    suspend fun confirm(input: Confirm.Input): Either<Confirm.Output, Confirm.Error>

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

    interface SignOut {
        @Serializable
        data class Input(
            @SerialName("access_token")
            val accessToken: String,
            @SerialName("refresh_token")
            val refreshToken: String,
        )

        object Output

        @Serializable
        data class Error(
            @SerialName("access_token")
            val accessToken: String,
            @SerialName("refresh_token")
            val refreshToken: String,
        )
    }

    interface Verify {
        @Serializable
        data class Input(val email: String)

        object Output

        @Serializable
        data class Error(val email: String = "")
    }

    interface Confirm {
        @Serializable
        data class Input(val email: String, val code: String)

        object Output

        @Serializable
        data class Error(val email: String = "", val code: String = "")
    }
}
