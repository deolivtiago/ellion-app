package com.clarxlabs.ellion.auth.domain.validation

import com.clarxlabs.ellion.auth.domain.validation.validators.EmailValidator
import com.clarxlabs.ellion.auth.domain.validation.validators.LengthValidator
import com.clarxlabs.ellion.auth.domain.validation.validators.LowerCaseValidator

object ValidatorStrategy {
    val EMAIL: TextValidator =
        TextValidatorComposite(listOf(LengthValidator(), EmailValidator()))
    val PASSWORD: TextValidator =
        TextValidatorComposite(listOf(LengthValidator(), LowerCaseValidator()))
}

data class TextValidatorStrategy(private val strategy: TextValidator) {
    val validate = strategy::validate
}
