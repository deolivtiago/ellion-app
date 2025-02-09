package com.clarxlabs.ellion.auth.presentation.signin

data class SignInModelState(
    val email: String = "",
    val emailErrorMessage: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val passwordErrorMessage: String = "",
    val isLoading: Boolean = false,
)

sealed interface SignInModelEvent {
    data class OnEmailChanged(val email: String): SignInModelEvent
    data class OnPasswordChanged(val password: String): SignInModelEvent
    data class OnSubmitClicked(val email: String, val password: String): SignInModelEvent
    data class OnResetPasswordClicked(val email: String): SignInModelEvent
    data class OnSignUpClicked(val email: String): SignInModelEvent
}

