package com.clarxlabs.ellion.auth.domain.validation.validators

import com.clarxlabs.ellion.application.utilities.Either
import com.clarxlabs.ellion.auth.domain.validation.TextValidator

class LengthValidator(
    val min: Int = 6, val max: Int = 160
) : TextValidator {

    override fun validate(text: String): Either<String, Error> {

        if (text.length < min) return Either.Failure(Error.TooShort(min))

        if (text.length > max) return Either.Failure(Error.TooLong(max))

        return Either.Success(text)
    }

    interface Error : TextValidator.Error {
        data class TooShort(val min: Int) : Error
        data class TooLong(val max: Int) : Error
    }
}
