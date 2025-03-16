package com.clarxlabs.ellion.auth.domain.validation.validators

import com.clarxlabs.ellion.application.utilities.Either
import com.clarxlabs.ellion.auth.domain.validation.TextValidator

class NumbersValidator(val min: Int = 1) : TextValidator {

    override fun validate(text: String): Either<String, Error> {
        val numbers = text.filter { it.isDigit() }

        if (numbers.length < min) return Either.Failure(Error.AtLeast(min))

        return Either.Success(text)
    }

    interface Error : TextValidator.Error {
        data class AtLeast(val min: Int) : Error
    }
}
