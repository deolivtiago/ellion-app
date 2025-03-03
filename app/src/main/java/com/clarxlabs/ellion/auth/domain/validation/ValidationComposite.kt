package com.clarxlabs.ellion.auth.domain.validation

import com.clarxlabs.ellion.auth.presentation.signin.TextFieldState
import com.clarxlabs.ellion.auth.presentation.signin.TextFieldType

object ValidationComposite {
    fun validate(fields: List<TextFieldState>): Map<TextFieldType, TextValidatorResult> {
        return fieldMap.mapValues { (type, value) ->
            type.validators
                .map { it.validate(value) }
                .firstOrNull { it != TextValidator.Result.VALID } // Return the first invalid result
                ?: TextValidator.Result.VALID // Default to VALID if all pass
        }
    }
}
