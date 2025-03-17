package com.clarxlabs.ellion.auth.presentation.signup

import androidx.lifecycle.viewModelScope
import com.clarxlabs.ellion.application.config.NavRoute
import com.clarxlabs.ellion.application.utilities.Either
import com.clarxlabs.ellion.application.utilities.validation.TextFieldValidation.Companion.errorMessageOf
import com.clarxlabs.ellion.application.utilities.validation.TextFieldValidation.Strategy
import com.clarxlabs.ellion.auth.domain.services.AuthenticationService
import com.clarxlabs.ellion.auth.domain.services.AuthenticationService.SignUp
import com.clarxlabs.ellion.auth.domain.services.ValidationService
import com.clarxlabs.ellion.auth.presentation.AppViewModel
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val authenticationService: AuthenticationService,
    private val validationService: ValidationService,
) : AppViewModel<SignUpModel.State, SignUpModel.Event>(SignUpModel.State()) {

    override fun onEvent(event: SignUpModel.Event) {
        when (event) {
            is SignUpModel.Event.OnEmailChanged -> onEmailChanged(event.text.trim())
            is SignUpModel.Event.OnFullNameChanged -> onNameChanged(event.text)
            is SignUpModel.Event.OnPasswordChanged -> onPasswordChanged(event.text.trim())
            is SignUpModel.Event.OnPasswordConfirmationChanged -> onConfirmationChanged(event.text.trim())
            is SignUpModel.Event.OnSubmitClicked -> onSubmitClicked(event.navigateTo)
            is SignUpModel.Event.OnPasswordVisibilityClicked -> onPasswordVisibilityClicked()
            is SignUpModel.Event.OnContactClicked -> onNavigate(event.navigateTo)
            is SignUpModel.Event.OnPoliciesClicked -> onNavigate(event.navigateTo)
            is SignUpModel.Event.OnResetPasswordClicked -> onNavigate(event.navigateTo)
            is SignUpModel.Event.OnSignInClicked -> onNavigate(event.navigateTo, NavRoute.SignIn)
            is SignUpModel.Event.OnTermsClicked -> onNavigate(event.navigateTo)
        }
    }

    private fun onNameChanged(text: String) {
        setState {
            it.copy(
                fullName = "${firstNameOf(text)} ${lastNameOf(text)}",
                fullNameError = errorMessageOf(
                    validationService.validate(firstNameOf(text), Strategy.FIRST_NAME)
                ).takeUnless { it.isEmpty() } ?: errorMessageOf(
                    validationService.validate(lastNameOf(text), Strategy.LAST_NAME)
                ),
            )
        }
        setState { it.copy(isFormValid = isFormValid()) }
    }

    private fun firstNameOf(fullName: String): String =
        fullName.replace("  ", " ").substringBefore(" ").trim().replaceFirstChar { it.uppercase() }

    private fun lastNameOf(fullName: String): String =
        fullName.replace("  ", " ").substringAfter(" ", "").trimStart().split(" ")
            .joinToString(" ") { if (it.length > 2) it.replaceFirstChar { it.uppercase() } else it }

    private fun onEmailChanged(text: String) {
        setState {
            it.copy(
                email = text,
                emailError = errorMessageOf(validationService.validate(text, Strategy.EMAIL))
            )
        }

        setState { it.copy(isFormValid = isFormValid()) }
    }

    private fun onPasswordChanged(text: String) {
        setState {
            it.copy(
                password = text,
                passwordError = errorMessageOf(validationService.validate(text, Strategy.PASSWORD)),
                passwordConfirmationError = confirmationError(text, it.passwordConfirmation),
            )
        }

        setState { it.copy(isFormValid = isFormValid()) }
    }

    private fun confirmationError(password: String, confirmation: String): String =
        "A senha e a confirmação devem ser iguais"
            .takeIf { password != confirmation }
            .orEmpty()

    private fun onConfirmationChanged(text: String) {
        setState {
            it.copy(
                passwordConfirmation = text,
                passwordConfirmationError = confirmationError(text, it.password),
            )
        }

        setState { it.copy(isFormValid = isFormValid()) }
    }

    private fun onSubmitClicked(navigateTo: (NavRoute) -> Unit) {
        setState { it.copy(isLoading = true, isPasswordVisible = false) }

        if (isFormValid())
            signUp { result ->
                when (result) {
                    is Either.Success -> navigateTo(NavRoute.Verify(state.value.email))
                    is Either.Failure -> setState {
                        it.copy(
                            fullNameError = result.output.firstName.plus(result.output.lastName),
                            emailError = result.output.email,
                            passwordError = result.output.password,
                        )
                    }
                }
            }

        setState { it.copy(isLoading = false) }
    }

    private fun onPasswordVisibilityClicked() {
        setState { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    private fun onNavigate(navigateTo: (NavRoute) -> Unit, route: NavRoute = NavRoute.Home()) {
        setState { it.copy(isPasswordVisible = false) }

        navigateTo(route)
    }

    private fun signUp(onResponse: (Either<SignUp.Output, SignUp.Error>) -> Unit) {
        val input = SignUp.Input(
            firstName = firstNameOf(state.value.fullName),
            lastName = lastNameOf(state.value.fullName).trimEnd(),
            email = state.value.email,
            password = state.value.password,
        )

        viewModelScope.launch { onResponse(authenticationService.signUp(input)) }
    }

    private fun isFormValid(): Boolean =
        validationService.isValid(
            mapOf(
                Strategy.EMAIL.to(state.value.email),
                Strategy.PASSWORD.to(state.value.password),
                Strategy.FIRST_NAME.to(firstNameOf(state.value.fullName)),
                Strategy.LAST_NAME.to(lastNameOf(state.value.fullName))
            )
        )
}
