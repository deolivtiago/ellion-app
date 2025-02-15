package com.clarxlabs.ellion.auth.presentation.signin

import com.clarxlabs.ellion.application.config.NavRoute

data class SignInModelState(
    val isLoading: Boolean = false,
    val email: String = "deoliv.tiago@gmail.com",
    val emailErrorMessage: String = "",
    val password: String = "4m1Mad?",
    val passwordErrorMessage: String = "",
    val isPasswordVisible: Boolean = false,
)

sealed interface SignInModelEvent {
    data class OnEmailChanged(val email: String) : SignInModelEvent
    data class OnPasswordChanged(val password: String) : SignInModelEvent
    object OnPasswordVisibilityClicked : SignInModelEvent
    object OnSubmitClicked : SignInModelEvent
    object OnResetPasswordClicked : SignInModelEvent
    object OnContactClicked : SignInModelEvent
    object OnTermsClicked : SignInModelEvent
    object OnPoliciesClicked : SignInModelEvent
    data class OnSignUpClicked(val navigateTo: (NavRoute) -> Unit) : SignInModelEvent
}
