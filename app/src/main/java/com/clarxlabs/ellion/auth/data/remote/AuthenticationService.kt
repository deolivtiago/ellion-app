package com.clarxlabs.ellion.auth.data.remote

import com.clarxlabs.ellion.application.utilities.Either
import kotlinx.serialization.Serializable

interface AuthenticationDataSource {
    suspend fun signIn(input: SignIn.Input): Either<SignIn.Output, SignIn.Error>
    suspend fun signUp(input: SignUp.Input): Either<SignUp.Output, SignUp.Error>
    suspend fun signOut(input: SignOut.Input): Either<SignOut.Output, SignOut.Error>
    suspend fun verify(input: Verify.Input): Either<Verify.Output, Verify.Error>
    suspend fun confirm(input: Confirm.Input): Either<Confirm.Output, Confirm.Error>
    suspend fun listUsers(): Either<ListUsers.Output, ListUsers.Error>

    sealed interface SignIn {
        @Serializable
        data class Input(
            override val email: String,
            override val password: String,
        ) : UserCredentials()

        @Serializable
        data class Output(
            override val accessToken: String,
            override val refreshToken: String
        ) : UserTokens()

        @Serializable
        data class Error(
            override val email: String = "",
            override val password: String = "",
        ) : UserCredentials()
    }

    sealed interface SignUp {
        @Serializable
        data class Input(
            override val email: String,
            override val password: String,
            override val firstName: String,
            override val lastName: String
        ) : UserData()

        @Serializable
        data class Output(
            override val id: String,
            override val email: String,
            override val firstName: String,
            override val lastName: String
        ) : User()

        @Serializable
        data class Error(
            override val email: String = "",
            override val password: String = "",
            override val firstName: String = "",
            override val lastName: String = "",
        ) : UserData()
    }

    sealed interface SignOut {
        @Serializable
        data class Input(
            override val accessToken: String,
            override val refreshToken: String
        ) : UserTokens()

        @Serializable
        data object Output

        @Serializable
        data class Error(
            override val accessToken: String = "",
            override val refreshToken: String = "",
        ) : UserTokens()
    }

    sealed interface Verify {
        @Serializable
        data class Input(val email: String)

        @Serializable
        data object Output

        @Serializable
        data class Error(val email: String = "")

    }

    sealed interface Confirm {
        @Serializable
        data class Input(val email: String, val code: String)

        @Serializable
        data object Output

        @Serializable
        data class Error(val email: String = "", val code: String = "")

    }

    sealed interface ListUsers {
        @Serializable
        data class Output(val users: List<User> = emptyList())

        @Serializable
        data class Error(val users: List<User> = emptyList())
    }
}

@Serializable
abstract class User {
    abstract val id: String
    abstract val email: String
    abstract val firstName: String
    abstract val lastName: String
}

@Serializable
abstract class UserData {
    abstract val email: String
    abstract val password: String
    abstract val firstName: String
    abstract val lastName: String
}

@Serializable
abstract class UserCredentials {
    abstract val email: String
    abstract val password: String
}

@Serializable
abstract class UserTokens {
    abstract val accessToken: String
    abstract val refreshToken: String
}
