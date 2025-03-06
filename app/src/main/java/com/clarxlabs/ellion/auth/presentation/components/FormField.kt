package com.clarxlabs.ellion.auth.presentation.components

import com.clarxlabs.ellion.auth.domain.validation.TextValidator
import com.clarxlabs.ellion.auth.domain.validation.TextValidatorComposite
import com.clarxlabs.ellion.auth.domain.validation.validators.EmailValidator
import com.clarxlabs.ellion.auth.domain.validation.validators.LengthValidator
import com.clarxlabs.ellion.auth.domain.validation.validators.RegexValidator
import kotlinx.serialization.Serializable

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

data class FieldMessage(
    val message: String = "",
    val isError: Boolean = false,
)

@Serializable
data class FieldState(
    val label: String,
    val text: String,
    val textError: String = "",
    val isVisible: Boolean = true,
//    val message: FieldMessage = FieldMessage(),
)

data class FormField<T>(val state: FieldState, val validator: T)

val emailState = FieldState("email", "")
val emailValidator = FieldValidator.Email()

val emailField = FormField(emailState, emailValidator)

val passwordState = FieldState("password", "")
val passwordValidator = FieldValidator.Password()

val passwordField = FormField(passwordState, passwordValidator)

val emailError = emailValidator
    .validate(emailState.text)
    .let { if (it == TextValidator.Result.INVALID_FORMAT) "Email inválido" else "" }

val isFormValid =
    listOf(emailField, passwordField)
        .map { it.validator.validate(it.state.text) }
        .all { it == TextValidator.Result.VALID }
