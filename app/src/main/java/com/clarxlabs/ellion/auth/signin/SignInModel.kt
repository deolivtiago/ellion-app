package com.clarxlabs.ellion.auth.signin

data class SignInModelState(
    val isLoading: Boolean = false,
    val email: String = "",
    val emailErrorMessage: String = "",
    val password: String = "",
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
    object OnSignUpClicked : SignInModelEvent
}
