package com.clarxlabs.ellion.auth.presentation.signup

import androidx.lifecycle.viewModelScope
import com.clarxlabs.ellion.application.config.NavRoute
import com.clarxlabs.ellion.application.utilities.Result
import com.clarxlabs.ellion.application.utilities.validation.TextFieldValidation.Companion.errorMessageOf
import com.clarxlabs.ellion.application.utilities.validation.TextFieldValidation.Strategy
import com.clarxlabs.ellion.auth.data.remote.AuthDataSource
import com.clarxlabs.ellion.auth.data.remote.dtos.UserData
import com.clarxlabs.ellion.auth.data.remote.dtos.UserDataError
import com.clarxlabs.ellion.auth.domain.entities.User
import com.clarxlabs.ellion.auth.domain.services.ValidationService
import com.clarxlabs.ellion.auth.presentation.AppViewModel
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val authDataSource: AuthDataSource,
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
                )
            )
        }
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
    }

    private fun onPasswordChanged(text: String) {
        setState {
            it.copy(
                password = text,
                passwordError = errorMessageOf(validationService.validate(text, Strategy.PASSWORD)),
                passwordConfirmationError = confirmationError(text, it.passwordConfirmation),
            )
        }
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
    }

    private fun onSubmitClicked(navigateTo: (NavRoute) -> Unit) {
        setState { it.copy(isLoading = true, isPasswordVisible = false) }

        signUp {
            when (it) {
                is Result.Data -> navigateTo(NavRoute.Verify(state.value.email))
                is Result.Error -> setState { state ->
                    state.copy(
                        fullNameError = it.error.firstName
                            .plus(it.error.lastName).firstOrNull() ?: "",
                        emailError = it.error.email
                            .firstOrNull() ?: "",
                        passwordError = it.error.password
                            .firstOrNull() ?: "",
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

    private fun signUp(onResponse: (Result<User, UserDataError>) -> Unit) {
        val input = UserData(
            firstName = firstNameOf(state.value.fullName),
            lastName = lastNameOf(state.value.fullName).trimEnd(),
            email = state.value.email,
            password = state.value.password,
        )

        viewModelScope.launch { onResponse(authDataSource.signUp(input)) }
    }
}
