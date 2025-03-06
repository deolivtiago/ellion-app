package com.clarxlabs.ellion.auth.domain.validation.validators

import com.clarxlabs.ellion.auth.domain.validation.TextValidator

class LengthValidator(val min: Int = 6, val max: Int = 160) : TextValidator {
    override fun validate(value: String): TextValidator.Result {
        
        if (value.length < min) return TextValidator.Result.TOO_SHORT

        if (value.length > max) return TextValidator.Result.TOO_LONG

        return TextValidator.Result.VALID
    }
}
