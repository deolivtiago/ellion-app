package com.clarxlabs.ellion.application.utilities.validation

import android.util.Patterns
import com.clarxlabs.ellion.application.utilities.Either

class EmailValidation : TextValidation {

    override fun validate(text: String): Either<String, Error> {
        val isFormatValid = Patterns.EMAIL_ADDRESS.matcher(text).matches()

        if (!isFormatValid) return Either.Failure(Error.InvalidFormat)

        return Either.Success(text)
    }

    sealed interface Error : TextValidation.Error {
        data object InvalidFormat : Error
    }
}
