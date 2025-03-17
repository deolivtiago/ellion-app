package com.clarxlabs.ellion.auth.domain.services

import com.clarxlabs.ellion.application.utilities.Either
import com.clarxlabs.ellion.application.utilities.validation.TextFieldValidation.Strategy
import com.clarxlabs.ellion.application.utilities.validation.TextValidation

interface ValidationService {
    fun validate(text: String, strategy: Strategy): Either<String, TextValidation.Error>
    fun isValid(fields: Map<Strategy, String> = emptyMap()): Boolean
}
