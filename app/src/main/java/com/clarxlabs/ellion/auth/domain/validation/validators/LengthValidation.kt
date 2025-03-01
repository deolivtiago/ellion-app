package com.clarxlabs.ellion.auth.domain.validation.validators

import com.clarxlabs.ellion.auth.domain.validation.ValidationStrategy

class LengthValidation(
    val min: Int = 6, minError: String = "O campo deve conter ao menos $min caracter(es)",
    val max: Int = 160, maxError: String = "O campo deve conter no máximo $max caracter(es)"
) : ValidationStrategy {
    override fun validate(value: String): ValidationStrategy.Result {

        if (value.length < min) return ValidationStrategy.Result.TOO_SHORT

        if (value.length < max) return ValidationStrategy.Result.TOO_LONG

        return ValidationStrategy.Result.VALID
    }
}
