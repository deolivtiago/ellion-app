package com.clarxlabs.ellion.core.repositories

import com.clarxlabs.ellion.core.datasources.AuthenticationDataSource
import com.clarxlabs.ellion.core.repositories.types.ConfirmError
import com.clarxlabs.ellion.core.repositories.types.ConfirmInput
import com.clarxlabs.ellion.core.repositories.types.ConfirmOutput
import com.clarxlabs.ellion.core.repositories.types.SignInError
import com.clarxlabs.ellion.core.repositories.types.SignInInput
import com.clarxlabs.ellion.core.repositories.types.SignInOutput
import com.clarxlabs.ellion.core.repositories.types.SignOutError
import com.clarxlabs.ellion.core.repositories.types.SignOutInput
import com.clarxlabs.ellion.core.repositories.types.SignOutOutput
import com.clarxlabs.ellion.core.repositories.types.SignUpError
import com.clarxlabs.ellion.core.repositories.types.SignUpInput
import com.clarxlabs.ellion.core.repositories.types.SignUpOutput
import com.clarxlabs.ellion.core.repositories.types.UserInfoError
import com.clarxlabs.ellion.core.repositories.types.UserInfoInput
import com.clarxlabs.ellion.core.repositories.types.UserInfoOutput
import com.clarxlabs.ellion.core.repositories.types.VerifyError
import com.clarxlabs.ellion.core.repositories.types.VerifyInput
import com.clarxlabs.ellion.core.repositories.types.VerifyOutput
import it.czerwinski.kotlin.util.Either

class AuthenticationRepositoryImpl(
    private val authenticationDataSource: AuthenticationDataSource,
) : AuthenticationRepository {
    override suspend fun signIn(input: SignInInput): Either<SignInError, SignInOutput> {
        return authenticationDataSource.signIn(input)
    }

    override suspend fun signUp(input: SignUpInput): Either<SignUpError, SignUpOutput> {
        return authenticationDataSource.signUp(input)
    }

    override suspend fun signOut(input: SignOutInput): Either<SignOutError, SignOutOutput> {
        return authenticationDataSource.signOut(input)
    }

    override suspend fun verify(input: VerifyInput): Either<VerifyError, VerifyOutput> {
        return authenticationDataSource.verify(input)
    }

    override suspend fun confirm(input: ConfirmInput): Either<ConfirmError, ConfirmOutput> {
        return authenticationDataSource.confirm(input)
    }

    override suspend fun userInfo(input: UserInfoInput): Either<UserInfoError, UserInfoOutput> {
        return authenticationDataSource.userInfo(input)
    }
}
