package com.clarxlabs.ellion.auth.domain.validation

import com.clarxlabs.ellion.auth.domain.validation.validators.EmailValidator
import com.clarxlabs.ellion.auth.domain.validation.validators.LengthValidator
import com.clarxlabs.ellion.auth.domain.validation.validators.RegExValidator
import kotlin.collections.listOf

interface ValidationStrategy {
    fun validate(data: String): Result

    enum class Type(val validators: List<ValidationStrategy>) {
        EMAIL(listOf(EmailValidator())),
        PASSWORD(listOf(LengthValidator(), RegExValidator(Regex("[0-9]")))),
    }

    enum class Result {
        INVALID_EMAIL,
        TOO_SHORT,
        TOO_LONG,
        MUST_HAVE,
        VALID,
    }
}
