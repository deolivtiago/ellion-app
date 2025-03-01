package com.clarxlabs.ellion.auth.domain.validation

import com.clarxlabs.ellion.auth.domain.validation.validators.EmailValidation
import com.clarxlabs.ellion.auth.domain.validation.validators.LengthValidation
import com.clarxlabs.ellion.auth.domain.validation.validators.RegExValidation
import kotlin.collections.listOf

interface ValidationStrategy {
    fun validate(data: String): Result

    enum class Type(val validators: List<ValidationStrategy>) {
        EMAIL(listOf(EmailValidation())),
        PASSWORD(listOf(LengthValidation(), RegExValidation(Regex("[0-9]")))),
    }

    enum class Result {
        INVALID_EMAIL,
        TOO_SHORT,
        TOO_LONG,
        MUST_HAVE,
        VALID,
    }
}
