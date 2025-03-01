package com.clarxlabs.ellion.auth.domain.validation.validators

import com.clarxlabs.ellion.auth.domain.validation.ValidationStrategy

class EmailValidation : ValidationStrategy {
    private val emailRegex = "^[.!?@#$%^&*_+a-z\\-0-9]+@[._+\\-a-z0-9]+$".toRegex()

    override fun validate(value: String): ValidationStrategy.Result {

        if (!value.matches(emailRegex)) return ValidationStrategy.Result.INVALID_EMAIL

        return ValidationStrategy.Result.VALID
    }
}
