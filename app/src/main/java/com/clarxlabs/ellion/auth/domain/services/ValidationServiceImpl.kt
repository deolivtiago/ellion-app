package com.clarxlabs.ellion.auth.domain.services

import com.clarxlabs.ellion.application.utilities.Either
import com.clarxlabs.ellion.application.utilities.validation.EmailValidation
import com.clarxlabs.ellion.application.utilities.validation.LengthValidation
import com.clarxlabs.ellion.application.utilities.validation.LowerCaseValidation
import com.clarxlabs.ellion.application.utilities.validation.NumbersValidation
import com.clarxlabs.ellion.application.utilities.validation.SymbolsValidation
import com.clarxlabs.ellion.application.utilities.validation.TextFieldValidation
import com.clarxlabs.ellion.application.utilities.validation.TextFieldValidation.Strategy
import com.clarxlabs.ellion.application.utilities.validation.TextValidation
import com.clarxlabs.ellion.application.utilities.validation.UpperCaseValidation
import com.clarxlabs.ellion.auth.domain.services.ValidationService.Validate
import kotlin.collections.Map.Entry

class ValidationServiceImpl : ValidationService {
    override fun validate(text: String, strategy: Strategy): Either<String, TextValidation.Error> =
        TextFieldValidation(strategy).validate(text)

    override fun validateFields(input: Validate.Input): Either<Validate.Output, Validate.Error> {
        val errors = input.fields
            .map(::validateField).toMap()
            .map(::mapFieldError).toMap()

        return errors.values.all { it.isEmpty() }
            .let {
                if (it) Either.Success(Validate.Output(input.fields))
                else Either.Failure(Validate.Error(errors))
            }
    }

    private fun validateField(it: Entry<Strategy, String>): Pair<Strategy, Either<String, TextValidation.Error>> =
        it.key.to(it.key.validator.validate(it.value))

    private fun mapFieldError(it: Entry<Strategy, Either<String, TextValidation.Error>>): Pair<Strategy, String> =
        it.key.to(errorMessage(it.value))

    fun errorMessage(it: Either<String, TextValidation.Error>): String =
        when (it) {
            is Either.Success -> ""
            is Either.Failure -> errorMessage(it.output)
        }

    fun errorMessage(it: TextValidation.Error): String =
        when (it) {
            is LengthValidation.Error.Required -> "é obrigatório"
            is LengthValidation.Error.TooLong -> "deve conter menos de ${it.max} caracter(es)"
            is LengthValidation.Error.TooShort -> "deve conter ao menos ${it.min} caracter(es)"
            is EmailValidation.Error.InvalidFormat -> "deve ter um formato válido"
            is NumbersValidation.Error.AtLeast -> "deve conter ao menos ${it.min} número(s)"
            is LowerCaseValidation.Error.AtLeast -> "deve conter ao menos ${it.min} minúscula(s)"
            is UpperCaseValidation.Error.AtLeast -> "deve conter ao menos ${it.min} maiúscula(s)"
            is SymbolsValidation.Error.AtLeast -> "deve conter ao menos ${it.min} símbolo(s). Ex: ${it.permitted}"

            is TextFieldValidation.Error -> "O campo ${it.strategy.label} ${errorMessage(it.error)}"
        }
}
