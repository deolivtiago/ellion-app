package com.clarxlabs.ellion.auth.presentation.signup

import com.clarxlabs.ellion.application.config.NavRoute

data class SignUpModelState(
    val isLoading: Boolean = false,
    val fullName: String = "Alice Liddel",
    val fullNameErrorMessage: String = "",
    val email: String = "alice@wonderland.co",
    val emailErrorMessage: String = "Email inválido",
    val password: String = "4m1Mad?",
    val passwordErrorMessage: String = "",
    val passwordConfirmation: String = "4m1Mad??",
    val passwordConfirmationErrorMessage: String = "As senhas devem ser iguais",
    val isPasswordVisible: Boolean = false,
)

sealed interface SignUpModelEvent {
    data class OnEmailChanged(val email: String) : SignUpModelEvent
    data class OnFullNameChanged(val fullName: String) : SignUpModelEvent
    data class OnPasswordChanged(val password: String) : SignUpModelEvent
    data class OnPasswordConfirmationChanged(val passwordConfirmation: String) : SignUpModelEvent
    object OnPasswordVisibilityClicked : SignUpModelEvent
    object OnSubmitClicked : SignUpModelEvent
    object OnResetPasswordClicked : SignUpModelEvent
    object OnContactClicked : SignUpModelEvent
    object OnTermsClicked : SignUpModelEvent
    object OnPoliciesClicked : SignUpModelEvent
    data class OnSignInClicked(val navigateTo: (NavRoute) -> Unit) : SignUpModelEvent
}
