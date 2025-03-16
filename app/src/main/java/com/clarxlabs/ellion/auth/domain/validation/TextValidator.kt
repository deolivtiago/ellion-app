package com.clarxlabs.ellion.auth.domain.validation

import com.clarxlabs.ellion.application.utilities.Either

interface TextValidator {
    fun validate(text: String): Either<String, Error>

    interface Error
}
