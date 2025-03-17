package com.clarxlabs.ellion.auth.domain.services

import com.clarxlabs.ellion.application.utilities.Either
import com.clarxlabs.ellion.auth.data.remote.AuthenticationDataSource
import com.clarxlabs.ellion.auth.domain.services.AuthenticationService.Confirm
import com.clarxlabs.ellion.auth.domain.services.AuthenticationService.SignIn
import com.clarxlabs.ellion.auth.domain.services.AuthenticationService.SignOut
import com.clarxlabs.ellion.auth.domain.services.AuthenticationService.SignUp
import com.clarxlabs.ellion.auth.domain.services.AuthenticationService.Verify

class AuthenticationServiceImpl(
    private val authenticationDataSource: AuthenticationDataSource,
) : AuthenticationService {

    override suspend fun signIn(it: SignIn.Input): Either<SignIn.Output, SignIn.Error> {
        val input = AuthenticationDataSource.SignIn.Input(
            email = it.email,
            password = it.password,
        )

        return authenticationDataSource.signIn(input).let {
            when (it) {
                is Either.Success -> Either.Success(
                    SignIn.Output(
                        it.output.accessToken,
                        it.output.refreshToken
                    )
                )

                is Either.Failure -> Either.Failure(
                    SignIn.Error(
                        it.output.email,
                        it.output.password
                    )
                )
            }
        }
    }

    override suspend fun signUp(it: SignUp.Input): Either<SignUp.Output, SignUp.Error> {
        val input = AuthenticationDataSource.SignUp.Input(
            email = it.email,
            password = it.password,
            firstName = it.firstName,
            lastName = it.lastName,
        )

        return authenticationDataSource.signUp(input).let {
            when (it) {
                is Either.Success -> Either.Success(
                    SignUp.Output(
                        id = it.output.id,
                        email = it.output.email,
                        firstName = it.output.firstName,
                        lastName = it.output.lastName,
                    )
                )

                is Either.Failure -> Either.Failure(
                    SignUp.Error(
                        email = it.output.email,
                        password = it.output.password,
                        firstName = it.output.firstName,
                        lastName = it.output.lastName,
                    )
                )
            }
        }
    }


    override suspend fun signOut(it: SignOut.Input): Either<SignOut.Output, SignOut.Error> {
        val input = AuthenticationDataSource.SignOut.Input(
            accessToken = it.accessToken,
            refreshToken = it.refreshToken,
        )

        return authenticationDataSource.signOut(input).let {
            when (it) {
                is Either.Success -> Either.Success(SignOut.Output)
                is Either.Failure -> Either.Failure(
                    SignOut.Error(
                        accessToken = it.output.accessToken,
                        refreshToken = it.output.refreshToken,
                    )
                )
            }
        }
    }

    override suspend fun verify(it: Verify.Input): Either<Verify.Output, Verify.Error> {
        val input = AuthenticationDataSource.Verify.Input(email = it.email)

        return authenticationDataSource.verify(input).let {
            when (it) {
                is Either.Success -> Either.Success(Verify.Output)
                is Either.Failure -> Either.Failure(Verify.Error(email = it.output.email))
            }
        }
    }

    override suspend fun confirm(it: Confirm.Input): Either<Confirm.Output, Confirm.Error> {
        val input = AuthenticationDataSource.Confirm.Input(
            email = it.email,
            code = it.code,
        )

        return authenticationDataSource.confirm(input).let {
            when (it) {
                is Either.Success -> Either.Success(Confirm.Output)
                is Either.Failure -> Either.Failure(
                    Confirm.Error(email = it.output.email, code = it.output.code)
                )
            }
        }
    }
}
