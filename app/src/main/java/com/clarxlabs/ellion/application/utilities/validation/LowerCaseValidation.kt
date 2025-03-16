package com.clarxlabs.ellion.application.utilities.validation

import com.clarxlabs.ellion.application.utilities.Either

class LowerCaseValidation(val min: Int = 1) : TextValidation {

    override fun validate(text: String): Either<String, Error> {
        val lowers = text.filter { it.isLowerCase() }

        if (lowers.length < min) return Either.Failure(Error.AtLeast(min))

        return Either.Success(text)
    }

    sealed interface Error : TextValidation.Error {
        data class AtLeast(val min: Int) : Error
    }
}
