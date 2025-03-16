package com.clarxlabs.ellion.application.utilities.validation

import com.clarxlabs.ellion.application.utilities.Either

class TextFieldValidation(val strategy: Strategy) : TextValidation {
    class Error(val strategy: Strategy, val error: TextValidation.Error) : TextValidation.Error

    enum class Strategy(val label: String, val validator: TextValidation) {
        EMAIL(
            "Email", TextValidationComposite(
                listOf(LengthValidation(isRequired = true), EmailValidation()),
            )
        ),
        PASSWORD(
            "Senha", TextValidationComposite(
                listOf(
                    LengthValidation(max = 72, isRequired = true),
                    NumbersValidation(),
                    LowerCaseValidation(),
                    UpperCaseValidation(),
                    SymbolsValidation(),
                ),
            )
        ),
        FIRST_NAME(
            "Nome", TextValidationComposite(
                listOf(LengthValidation(2, 128, true)),
            )
        ),
        LAST_NAME(
            "Sobrenome", TextValidationComposite(
                listOf(LengthValidation(0, 128)),
            )
        ),
    }

    override fun validate(text: String): Either<String, Error> {
        return strategy.validator
            .validate(text).let {
                when (it) {
                    is Either.Success -> Either.Success(it.output)
                    is Either.Failure -> Either.Failure(Error(strategy, it.output))
                }
            }
    }

    companion object {
        fun errorMessageOf(it: Either<String, TextValidation.Error>): String =
            when (it) {
                is Either.Success -> ""
                is Either.Failure -> errorMessageOf(it.output)
            }

        fun errorMessageOf(it: TextValidation.Error): String =
            when (it) {
                is LengthValidation.Error.Required -> "é obrigatório"
                is LengthValidation.Error.TooLong -> "deve conter menos de ${it.max} caracter(es)"
                is LengthValidation.Error.TooShort -> "deve conter ao menos ${it.min} caracter(es)"
                is EmailValidation.Error.InvalidFormat -> "deve ter um formato válido"
                is NumbersValidation.Error.AtLeast -> "deve conter ao menos ${it.min} número(s)"
                is LowerCaseValidation.Error.AtLeast -> "deve conter ao menos ${it.min} caracter(es) minúsculo(s)"
                is UpperCaseValidation.Error.AtLeast -> "deve conter ao menos ${it.min} caracter(es) maiúsculo(s)"
                is SymbolsValidation.Error.AtLeast -> "deve conter ao menos ${it.min} símbolo(s). Ex: ${it.permitted}"

                is TextFieldValidation.Error -> "O campo ${it.strategy.label} ${errorMessageOf(it.error)}"
            }
    }
}
