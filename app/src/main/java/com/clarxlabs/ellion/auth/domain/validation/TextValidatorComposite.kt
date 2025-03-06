package com.clarxlabs.ellion.auth.domain.validation

data class TextValidatorComposite(
    val validators: List<TextValidator> = emptyList(),
) : TextValidator {
    override fun validate(text: String): TextValidator.Result =
        validators
            .map { it.validate(text) }
            .firstOrNull { it != TextValidator.Result.VALID }
            ?: TextValidator.Result.VALID
}
