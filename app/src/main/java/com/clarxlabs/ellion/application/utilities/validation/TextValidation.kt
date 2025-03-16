package com.clarxlabs.ellion.application.utilities.validation

import com.clarxlabs.ellion.application.utilities.Either

sealed interface TextValidation {
    fun validate(text: String): Either<String, Error>

    sealed interface Error
}
