package com.clarxlabs.ellion.auth.domain.validation.validators

import com.clarxlabs.ellion.auth.domain.validation.TextValidator

class EmailValidator : TextValidator {
    private val emailRegex = "^[.!?@#$%^&*_+a-z\\-0-9]+@[._+\\-a-z0-9]+$".toRegex()

    override fun validate(value: String): TextValidator.Result {

        if (!value.matches(emailRegex)) return TextValidator.Result.INVALID_FORMAT

        return TextValidator.Result.VALID
    }
}
