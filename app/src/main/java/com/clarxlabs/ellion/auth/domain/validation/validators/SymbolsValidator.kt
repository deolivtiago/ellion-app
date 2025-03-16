package com.clarxlabs.ellion.auth.domain.validation.validators

import com.clarxlabs.ellion.application.utilities.Either
import com.clarxlabs.ellion.auth.domain.validation.TextValidator

class SymbolsValidator(
    val permitted: String = ".!?@#%^&*_+-$,",
    val min: Int = 1,
) : TextValidator {

    override fun validate(text: String): Either<String, Error> {
        val symbols = text.filter { permitted.contains(it) }

        if (symbols.length < min) return Either.Failure(Error.AtLeast(min))

        return Either.Success(text)
    }

    interface Error : TextValidator.Error {
        data class AtLeast(val min: Int) : Error
    }
}
