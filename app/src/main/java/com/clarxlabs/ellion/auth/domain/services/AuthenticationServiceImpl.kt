package com.clarxlabs.ellion.auth.domain.services

import com.clarxlabs.ellion.application.utilities.Either
import com.clarxlabs.ellion.application.utilities.Result
import com.clarxlabs.ellion.auth.data.remote.AuthDataSource
import com.clarxlabs.ellion.auth.data.remote.dtos.CredentialsData
import com.clarxlabs.ellion.auth.data.remote.dtos.UserData
import com.clarxlabs.ellion.auth.domain.services.AuthenticationService.Confirm
import com.clarxlabs.ellion.auth.domain.services.AuthenticationService.SignIn
import com.clarxlabs.ellion.auth.domain.services.AuthenticationService.SignOut
import com.clarxlabs.ellion.auth.domain.services.AuthenticationService.SignUp
import com.clarxlabs.ellion.auth.domain.services.AuthenticationService.Verify
import com.clarxlabs.ellion.auth.domain.validation.TextValidator
import com.clarxlabs.ellion.auth.domain.validation.validators.EmailValidator
import com.clarxlabs.ellion.auth.domain.validation.validators.LengthValidator
import com.clarxlabs.ellion.auth.domain.validation.validators.LowerCaseValidator
import com.clarxlabs.ellion.auth.domain.validation.validators.NumbersValidator
import com.clarxlabs.ellion.auth.domain.validation.validators.SymbolsValidator
import com.clarxlabs.ellion.auth.domain.validation.validators.UpperCaseValidator

class AuthenticationServiceImpl(
    private val authDataSource: AuthDataSource,
) : AuthenticationService {
    override suspend fun signIn(input: SignIn.Input): Either<SignIn.Output, SignIn.Error> {
//        return validateInput(input).let {
//            when (it) {
//                is Either.Failure -> Either.Failure(it.output)
//                is Either.Success -> doSignIn(it.output)
//            }
//        }

        return doSignIn(input)
    }

    override suspend fun signUp(input: SignUp.Input): Either<SignUp.Output, SignUp.Error> {
//        return validateInput(input).let {
//            when (it) {
//                is Either.Failure -> Either.Failure(it.output)
//                is Either.Success -> doSignUp(it.output)
//            }
//        }
        return doSignUp(input)
    }

    override suspend fun signOut(input: SignOut.Input): Either<SignOut.Output, SignOut.Error> {
        TODO("Not yet implemented")
    }

    override suspend fun verify(input: Verify.Input): Either<Verify.Output, Verify.Error> {
        TODO("Not yet implemented")
    }

    override suspend fun confirm(input: Confirm.Input): Either<Confirm.Output, Confirm.Error> {
        TODO("Not yet implemented")
    }

    private suspend fun doSignIn(it: SignIn.Input): Either<SignIn.Output, SignIn.Error> {
        val input = CredentialsData(
            email = it.email,
            password = it.password,
        )

        authDataSource.signIn(input).let {
            return when (it) {
                is Result.Data -> Either.Success(
                    SignIn.Output(
                        accessToken = it.data.accessToken,
                        refreshToken = it.data.refreshToken,
                    )
                )

                is Result.Error -> Either.Failure(
                    SignIn.Error(
                        email = it.error.email.first(),
                        password = it.error.password.first(),
                    )
                )
            }
        }
    }

    private suspend fun doSignUp(it: SignUp.Input): Either<SignUp.Output, SignUp.Error> {
        val input = UserData(
            email = it.email,
            password = it.password,
            firstName = it.firstName,
            lastName = it.lastName,
            role = it.role
        )

        authDataSource.signUp(input).let {
            return when (it) {
                is Result.Data -> Either.Success(
                    SignUp.Output(
                        id = it.data.id,
                        email = it.data.email,
                        firstName = it.data.firstName,
                        lastName = it.data.lastName,
                    )
                )

                is Result.Error -> Either.Failure(
                    SignUp.Error(
                        email = it.error.email.first(),
                        password = it.error.password.first(),
                        firstName = it.error.firstName.first(),
                        lastName = it.error.lastName.first(),
                        role = it.error.role.first(),
                    )
                )
            }
        }
    }

//    private fun validateInput(it: SignIn.Input): Either<SignIn.Input, SignIn.Failure> {
//        val errors = mapOf(
//            ValidationType.EMAIL.to(it.email),
//            ValidationType.PASSWORD.to(it.password)
//        )
//            .map(::validateField).toMap()
//            .map(::mapFieldError).toMap()
//
//        return errors.values.all { it.isEmpty() }
//            .let { isValid ->
//                if (isValid) Either.Success(it)
//                else Either.Failure(
//                    SignIn.Failure(
//                        email = errors[ValidationType.EMAIL]!!,
//                        password = errors[ValidationType.PASSWORD]!!,
//                    )
//                )
//            }
//    }
//
//    private fun validateInput(it: SignUp.Input): Either<SignUp.Input, SignUp.Failure> {
//        val errors = mapOf(
//            ValidationType.EMAIL.to(it.email),
//            ValidationType.PASSWORD.to(it.password),
//            ValidationType.FIRST_NAME.to(it.firstName),
//            ValidationType.LAST_NAME.to(it.lastName),
//        )
//            .map(::validateField).toMap<ValidationType, String>()
//            .map(::mapFieldError).toMap()
//
//        return
////        errors.values.all { it.isEmpty() }
////            .let { isValid ->
////                if (isValid) Either.Success(it)
////                else
//                    Either.Failure(
//                    SignUp.Failure(
//                        email = errors[ValidationType.EMAIL]!!,
//                        password = errors[ValidationType.PASSWORD]!!,
//                        firstName = errors[ValidationType.FIRST_NAME]!!,
//                        lastName = errors[ValidationType.LAST_NAME]!!,
//                    )
//                )
////            }
//    }

    private fun mapError(it: TextValidator.Error) =
        when (it) {

            is EmailValidator.Error.InvalidFormat -> "Campo inválido"
            is LengthValidator.Error.TooShort -> "Campo muito curto"
            is LengthValidator.Error.TooLong -> "Campo muito longo"
            is LowerCaseValidator.Error.AtLeast -> "O campo deve conter letras minúsculas"
            is UpperCaseValidator.Error.AtLeast -> "O campo deve conter letras maiúsculas"
            is NumbersValidator.Error.AtLeast -> "O campo deve conter números"
            is SymbolsValidator.Error.AtLeast -> "O campo deve conter symbolos"
            else -> ""
        }
}
