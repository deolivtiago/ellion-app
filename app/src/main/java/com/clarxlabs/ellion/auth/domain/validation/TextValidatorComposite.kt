package com.clarxlabs.ellion.auth.domain.validation

import com.clarxlabs.ellion.application.utilities.Either

data class TextValidatorComposite(
    val validators: List<TextValidator> = emptyList(),
) : TextValidator {
    override fun validate(text: String): Either<String, TextValidator.Error> =
        validators
            .map { it.validate(text) }
            .first()
}
