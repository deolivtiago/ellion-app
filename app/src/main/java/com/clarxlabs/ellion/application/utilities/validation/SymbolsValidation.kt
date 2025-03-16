package com.clarxlabs.ellion.application.utilities.validation

import com.clarxlabs.ellion.application.utilities.Either

class SymbolsValidation(
    val permitted: String = ".!?@#%^&*_+-$,",
    val min: Int = 1,
) : TextValidation {

    override fun validate(text: String): Either<String, Error> {
        val symbols = text.filter { permitted.contains(it) }

        if (symbols.length < min) return Either.Failure(Error.AtLeast(min, permitted))

        return Either.Success(text)
    }

    sealed interface Error : TextValidation.Error {
        data class AtLeast(val min: Int, val permitted: String) : Error
    }
}
