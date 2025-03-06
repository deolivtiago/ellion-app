package com.clarxlabs.ellion.auth.domain.services

import com.clarxlabs.ellion.application.utilities.Either
import com.clarxlabs.ellion.application.utilities.Result
import com.clarxlabs.ellion.auth.data.remote.AuthDataSource
import com.clarxlabs.ellion.auth.data.remote.dtos.CredentialsData
import com.clarxlabs.ellion.auth.data.remote.dtos.UserData
import com.clarxlabs.ellion.auth.domain.services.AuthenticationService.SignIn
import com.clarxlabs.ellion.auth.domain.services.AuthenticationService.SignUp
import com.clarxlabs.ellion.auth.domain.validation.TextValidator
import kotlin.collections.Map.Entry

class AuthenticationServiceImpl(
    private val authDataSource: AuthDataSource,
) : AuthenticationService {
    override suspend fun signIn(input: SignIn.Input): Either<SignIn.Output, SignIn.Error> {
        return validateInput(input).let {
            when (it) {
                is Either.Error -> Either.Error(it.output)
                is Either.Success -> doSignIn(it.output)
            }
        }
    }

    override suspend fun signUp(input: SignUp.Input): Either<SignUp.Output, SignUp.Error> {
        return validateInput(input).let {
            when (it) {
                is Either.Error -> Either.Error(it.output)
                is Either.Success -> doSignUp(it.output)
            }
        }
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

                is Result.Error -> Either.Error(
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

                is Result.Error -> Either.Error(
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

    private fun validateInput(it: SignIn.Input): Either<SignIn.Input, SignIn.Error> {
        val errors = mapOf(
            ValidationType.EMAIL.to(it.email),
            ValidationType.PASSWORD.to(it.password)
        )
            .map(::validateField).toMap()
            .map(::mapFieldError).toMap()

        return errors.values.all { it.isEmpty() }
            .let { isValid ->
                if (isValid) Either.Success(it)
                else Either.Error(
                    SignIn.Error(
                        email = errors[ValidationType.EMAIL]!!,
                        password = errors[ValidationType.PASSWORD]!!,
                    )
                )
            }
    }

    private fun validateInput(it: SignUp.Input): Either<SignUp.Input, SignUp.Error> {
        val errors = mapOf(
            ValidationType.EMAIL.to(it.email),
            ValidationType.PASSWORD.to(it.password),
            ValidationType.FIRST_NAME.to(it.firstName),
            ValidationType.LAST_NAME.to(it.lastName),
        )
            .map(::validateField).toMap()
            .map(::mapFieldError).toMap()

        return errors.values.all { it.isEmpty() }
            .let { isValid ->
                if (isValid) Either.Success(it)
                else Either.Error(
                    SignUp.Error(
                        email = errors[ValidationType.EMAIL]!!,
                        password = errors[ValidationType.PASSWORD]!!,
                        firstName = errors[ValidationType.FIRST_NAME]!!,
                        lastName = errors[ValidationType.LAST_NAME]!!,
                    )
                )
            }
    }

    private fun validateField(it: Entry<ValidationType, String>) =
        it.key.to(TextValidatorFactory.create(it.key).validate(it.value))

    private fun mapFieldError(it: Entry<ValidationType, TextValidator.Result>) =
        when (it.key) {
            ValidationType.EMAIL -> it.key.to(mapEmailError(it.value))
            ValidationType.PASSWORD -> it.key.to(mapPasswordError(it.value))
            ValidationType.FIRST_NAME -> it.key.to(mapNameError(it.value))
            ValidationType.LAST_NAME -> it.key.to(mapNameError(it.value))
        }

    private fun mapPasswordError(it: TextValidator.Result) =
        when (it) {
            TextValidator.Result.TOO_SHORT -> "Senha muito curta"
            TextValidator.Result.TOO_LONG -> "Senha muito longa"
            TextValidator.Result.MUST_HAVE -> "A senha deve conter letras maiúsculas, minúsculas, números, e símbolos"
            else -> ""
        }

    private fun mapEmailError(it: TextValidator.Result) =
        when (it) {
            TextValidator.Result.INVALID_FORMAT -> "Email inválido"
            TextValidator.Result.TOO_SHORT -> "Email muito curto"
            TextValidator.Result.TOO_LONG -> "Email muito longo"
            else -> ""
        }

    private fun mapNameError(it: TextValidator.Result) =
        when (it) {
            TextValidator.Result.TOO_SHORT -> "Nome é obrigatório"
            TextValidator.Result.TOO_LONG -> "Nome muito longo"
            else -> ""
        }
}
