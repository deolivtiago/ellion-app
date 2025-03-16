package com.clarxlabs.ellion.auth.presentation.signin

import androidx.lifecycle.viewModelScope
import com.clarxlabs.ellion.application.config.NavRoute
import com.clarxlabs.ellion.application.utilities.Either
import com.clarxlabs.ellion.application.utilities.validation.TextFieldValidation.Companion.errorMessageOf
import com.clarxlabs.ellion.application.utilities.validation.TextFieldValidation.Strategy
import com.clarxlabs.ellion.auth.domain.services.AuthenticationService
import com.clarxlabs.ellion.auth.domain.services.AuthenticationService.SignIn
import com.clarxlabs.ellion.auth.domain.services.ValidationService
import com.clarxlabs.ellion.auth.domain.services.ValidationService.Validate
import com.clarxlabs.ellion.auth.presentation.AppViewModel
import kotlinx.coroutines.launch

class SignInViewModel(
    private val validationService: ValidationService,
    private val authenticationService: AuthenticationService,
) : AppViewModel<SignInModel.State, SignInModel.Event>(SignInModel.State()) {

    override fun onEvent(event: SignInModel.Event) {
        when (event) {
            is SignInModel.Event.OnEmailChanged -> onEmailChanged(event.email.trim())
            is SignInModel.Event.OnPasswordChanged -> onPasswordChanged(event.password.trim())
            is SignInModel.Event.OnPasswordVisibilityClicked -> onPasswordVisibilityChanged()
            is SignInModel.Event.OnResetPasswordClicked -> onResetPasswordClicked()
            is SignInModel.Event.OnSignUpClicked -> onSignUpClicked(event.navigateTo)
            is SignInModel.Event.OnContactClicked -> onContactClicked()
            is SignInModel.Event.OnSubmitClicked -> onSubmitClicked(event.navigateTo)
            is SignInModel.Event.OnTermsClicked -> onTermsClicked(event.navigateTo)
            is SignInModel.Event.OnPoliciesClicked -> onPoliciesClicked()
        }
    }

    private fun onPoliciesClicked() {
        setState { it.copy(isLoading = false, isPasswordVisible = false) }
    }

    private fun onTermsClicked(navigateTo: (NavRoute) -> Unit) {
        setState { it.copy(isLoading = false, isPasswordVisible = false) }

        navigateTo(NavRoute.Home())
    }

    private fun onSubmitClicked(navigateTo: (NavRoute) -> Unit) {
        setState { it.copy(isLoading = true) }

        signIn { result ->
            when (result) {
                is Either.Success -> navigateTo(
                    NavRoute.Home(
                        accessToken = result.output.accessToken,
                        refreshToken = result.output.refreshToken,
                    )
                )

                is Either.Failure -> {
                    if (result.output.email == "must be verified")
                        navigateTo(NavRoute.Verify(state.value.email))
                    else
                        setState {
                            it.copy(
                                emailError = result.output.email,
                                passwordError = result.output.password
                            )
                        }
                }
            }
        }

        setState { it.copy(isLoading = false) }
    }

    private fun onContactClicked() {
        setState { it.copy(isLoading = false, isPasswordVisible = false) }
    }

    private fun onSignUpClicked(navigateTo: (NavRoute) -> Unit) {
        setState { it.copy(isPasswordVisible = false) }

        navigateTo(NavRoute.SignUp)
    }

    private fun onResetPasswordClicked() {
        setState { it.copy(isPasswordVisible = false) }
    }

    private fun onPasswordVisibilityChanged() {
        setState { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    private fun onPasswordChanged(text: String) {
        setState {
            it.copy(
                password = text,
                passwordError = validationService
                    .validate(text, Strategy.PASSWORD)
                    .let { errorMessageOf(it) },
            )
        }
    }

    private fun onEmailChanged(text: String) {
        setState {
            it.copy(
                email = text,
                emailError = validationService
                    .validate(text, Strategy.EMAIL)
                    .let { errorMessageOf(it) },
            )
        }
    }

    private fun signIn(onResponse: (Either<SignIn.Output, SignIn.Error>) -> Unit) {
        val input = SignIn.Input(
            email = state.value.email,
            password = state.value.password
        )

        viewModelScope.launch { onResponse(authenticationService.signIn(input)) }
    }

    private fun validateFields(onResult: (Either<Validate.Output, Validate.Error>) -> Unit) {
        val input = Validate.Input(
            mapOf(
                Strategy.EMAIL.to(state.value.email),
                Strategy.PASSWORD.to(state.value.password),
            )
        )

        viewModelScope.launch { onResult(validationService.validateFields(input)) }
    }
}
