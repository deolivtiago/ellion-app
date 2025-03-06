package com.clarxlabs.ellion.auth.presentation.components

import com.clarxlabs.ellion.auth.domain.validation.TextValidator
import com.clarxlabs.ellion.auth.domain.validation.TextValidatorComposite
import com.clarxlabs.ellion.auth.domain.validation.validators.EmailValidator
import com.clarxlabs.ellion.auth.domain.validation.validators.LengthValidator
import com.clarxlabs.ellion.auth.domain.validation.validators.RegexValidator

val emailValidatorComposite =
    TextValidatorComposite(listOf(LengthValidator(), EmailValidator()))
val passwordValidatorComposite =
    TextValidatorComposite(listOf(LengthValidator(), RegexValidator(Regex("\\d+"))))
val fullNameValidatorComposite =
    TextValidatorComposite(listOf(LengthValidator(2)))


sealed interface FieldValidator {
    val validate: (text: String) -> TextValidator.Result

    data class Email(
        override val validate: (text: String) -> TextValidator.Result = emailValidatorComposite::validate
    ) : FieldValidator

    data class Password(
        override val validate: (text: String) -> TextValidator.Result = passwordValidatorComposite::validate
    ) : FieldValidator

    data class FullName(
        override val validate: (text: String) -> TextValidator.Result = fullNameValidatorComposite::validate
    ) : FieldValidator
}

//    data class PasswordConfirmation(
//        val validate: (text: String, other: String) -> TextValidator.Result = { _, _ -> TextValidator.Result.VALID }
//    ) : FieldValidator


data class Field<T>(val text: String, val validator: T)

val emailValidator = FieldValidator.Email()
val passwordValidator = FieldValidator.Password()

val emailField = Field("", emailValidator)
val passwordField = Field("", passwordValidator)

val emailError = emailValidator
    .validate(emailField.text)
    .let { if (it == TextValidator.Result.INVALID_FORMAT) "Email inválido" else "" }

val isFormValid =
    listOf(emailField, passwordField)
        .map { it.validator.validate(it.text) }
        .all { it == TextValidator.Result.VALID }
