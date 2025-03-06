package com.clarxlabs.ellion.auth.domain.validation

import com.clarxlabs.ellion.auth.domain.validation.validators.EmailValidator
import com.clarxlabs.ellion.auth.domain.validation.validators.LengthValidator
import com.clarxlabs.ellion.auth.domain.validation.validators.RegexValidator
import com.clarxlabs.ellion.auth.presentation.components.emailValidatorComposite
import com.clarxlabs.ellion.auth.presentation.components.fullNameValidatorComposite
import com.clarxlabs.ellion.auth.presentation.components.passwordValidatorComposite


data class FieldType(val type: ValidatorFactory.Type, val validator: TextValidator)

object ValidatorFactory {
    private val emailValidator =
        TextValidatorComposite(listOf(LengthValidator(), EmailValidator()))
    private val passwordValidator =
        TextValidatorComposite(listOf(LengthValidator(), RegexValidator(Regex("\\d+"))))
    private val fullNameValidator =
        TextValidatorComposite(listOf(LengthValidator(2)))

    enum class Type(val validate: (text: String) -> TextValidator.Result = TextValidatorComposite()::validate) {
        EMAIL(emailValidatorComposite::validate),
        PASSWORD(passwordValidatorComposite::validate),
        FULL_NAME(fullNameValidatorComposite::validate),
        PASSWORD_CONFIRMATION,
    }

    fun create() = ValidatorStrategy.EMAIL
}
