package com.clarxlabs.ellion.auth.domain.validation.validators

import com.clarxlabs.ellion.auth.domain.validation.ValidationStrategy

class RegExValidator(val regex: Regex) : ValidationStrategy {
    override fun validate(value: String): ValidationStrategy.Result {

        if (!value.matches(regex)) return ValidationStrategy.Result.MUST_HAVE

        return ValidationStrategy.Result.VALID
    }
}
