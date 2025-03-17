package com.clarxlabs.ellion.auth.domain.services

import com.clarxlabs.ellion.application.utilities.Either
import com.clarxlabs.ellion.application.utilities.validation.TextFieldValidation
import com.clarxlabs.ellion.application.utilities.validation.TextFieldValidation.Strategy
import com.clarxlabs.ellion.application.utilities.validation.TextValidation

class ValidationServiceImpl : ValidationService {
    override fun validate(text: String, strategy: Strategy): Either<String, TextValidation.Error> =
        TextFieldValidation(strategy).validate(text)

    override fun isValid(fields: Map<Strategy, String>): Boolean = fields
        .map { it.key.to(validate(it.value, it.key)) }.toMap()
        .values.all { it is Either.Success }
}
