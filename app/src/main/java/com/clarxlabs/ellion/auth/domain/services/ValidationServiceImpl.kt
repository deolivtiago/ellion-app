package com.clarxlabs.ellion.auth.domain.services

import com.clarxlabs.ellion.application.utilities.Either
import com.clarxlabs.ellion.auth.domain.validation.TextValidator
import com.clarxlabs.ellion.auth.domain.validation.TextValidatorComposite
import com.clarxlabs.ellion.auth.domain.validation.validators.EmailValidator
import com.clarxlabs.ellion.auth.domain.validation.validators.LengthValidator
import kotlin.collections.Map.Entry

enum class ValidationType { EMAIL, PASSWORD, FIRST_NAME, LAST_NAME }

object TextValidatorFactory {
    fun create(type: ValidationType): TextValidator = type.let {
        when (it) {
            ValidationType.EMAIL ->
                TextValidatorComposite(listOf(LengthValidator(), EmailValidator()))

            ValidationType.PASSWORD ->
                LengthValidator()

            ValidationType.FIRST_NAME ->
                LengthValidator(min = 1)

            ValidationType.LAST_NAME ->
                LengthValidator(min = 0)
        }
    }
}

class ValidationServiceImpl(
    val validatorFactory: TextValidatorFactory
) : ValidationService {
    override suspend fun execute(input: ValidationService.Input): Either<ValidationService.Output, ValidationService.Error> {
        val errors = mapOf(
            ValidationType.EMAIL.to(input.email),
            ValidationType.PASSWORD.to(input.password),
        )
            .map(::validateField).toMap()
            .map(::mapFieldError).toMap()

        return errors.values.all { it.isEmpty() }
            .let {
                if (it) Either.Success(ValidationService.Output(input.email, input.email))
                else Either.Error(
                    ValidationService.Error(
                        listOf(errors[ValidationType.EMAIL]!!),
                        listOf(errors[ValidationType.PASSWORD]!!),
                    )
                )
            }
    }

    private fun validateField(it: Entry<ValidationType, String>) =
        it.key.to(validatorFactory.create(it.key).validate(it.value))

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
