package com.clarxlabs.ellion.application.utilities.validation

import com.clarxlabs.ellion.application.utilities.Either

class LengthValidation(
    val min: Int = 6, val max: Int = 160, val isRequired: Boolean = false
) : TextValidation {

    override fun validate(text: String): Either<String, Error> {
        if (isRequired and text.isBlank()) return Either.Failure(Error.Required)

        if (text.isNotBlank()) {
            if (text.length < min) return Either.Failure(Error.TooShort(min))
            if (text.length > max) return Either.Failure(Error.TooLong(max))
        }
        
        return Either.Success(text)
    }

    sealed interface Error : TextValidation.Error {
        data object Required : Error
        data class TooShort(val min: Int) : Error
        data class TooLong(val max: Int) : Error
    }
}
