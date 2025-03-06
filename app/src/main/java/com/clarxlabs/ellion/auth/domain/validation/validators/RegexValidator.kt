package com.clarxlabs.ellion.auth.domain.validation.validators

import com.clarxlabs.ellion.auth.domain.validation.TextValidator

class RegexValidator(val regex: Regex) : TextValidator {
    override fun validate(value: String): TextValidator.Result {

        if (!value.matches(regex)) return TextValidator.Result.MUST_HAVE

        return TextValidator.Result.VALID
    }
}
