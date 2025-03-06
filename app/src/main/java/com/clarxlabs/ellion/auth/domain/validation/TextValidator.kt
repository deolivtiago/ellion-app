package com.clarxlabs.ellion.auth.domain.validation

interface TextValidator {
    fun validate(text: String): Result

    enum class Result {
        INVALID_FORMAT,
        TOO_SHORT,
        TOO_LONG,
        MUST_HAVE,
        VALID,
    }
}
