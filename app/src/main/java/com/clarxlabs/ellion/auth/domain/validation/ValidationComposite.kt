package com.clarxlabs.ellion.auth.domain.validation

import com.clarxlabs.ellion.auth.presentation.signin.TextFieldState
import com.clarxlabs.ellion.auth.presentation.signin.TextFieldType

object ValidationComposite {
    fun validate(fields: List<TextFieldState>): Map<TextFieldType, TextValidator.Result> {
        return fields.map { field ->
            field.type.to(
                field.type
                    .validators
                    .map { it.validate(field.value) }
                    .firstOrNull { it != TextValidator.Result.VALID }
                    ?: TextValidator.Result.VALID
            )
        }.toMap()
    }
}
