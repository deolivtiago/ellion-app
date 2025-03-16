package com.clarxlabs.ellion.application.utilities.validation

import com.clarxlabs.ellion.application.utilities.Either

class NumbersValidation(val min: Int = 1) : TextValidation {

    override fun validate(text: String): Either<String, Error> {
        val numbers = text.filter { it.isDigit() }

        if (numbers.length < min) return Either.Failure(Error.AtLeast(min))

        return Either.Success(text)
    }

    sealed interface Error : TextValidation.Error {
        data class AtLeast(val min: Int) : Error
    }
}
