package com.clarxlabs.ellion.application.utilities.validation

import com.clarxlabs.ellion.application.utilities.Either

class TextValidationComposite(val validators: List<TextValidation> = emptyList()) : TextValidation {
    override fun validate(text: String): Either<String, TextValidation.Error> =
        validators
            .map { it.validate(text) }
            .firstOrNull { it is Either.Failure }
            ?: Either.Success(text)
}
