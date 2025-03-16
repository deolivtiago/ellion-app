package com.clarxlabs.ellion.auth.domain.services

import com.clarxlabs.ellion.application.utilities.Either
import com.clarxlabs.ellion.application.utilities.validation.TextFieldValidation.Strategy
import com.clarxlabs.ellion.application.utilities.validation.TextValidation

interface ValidationService {
    fun validate(text: String, strategy: Strategy): Either<String, TextValidation.Error>
    fun validateFields(input: Validate.Input): Either<Validate.Output, Validate.Error>

    sealed interface Validate {
        data class Input(val fields: Map<Strategy, String> = emptyMap())
        data class Output(val fields: Map<Strategy, String> = emptyMap())
        data class Error(val fields: Map<Strategy, String> = emptyMap())
    }
}
