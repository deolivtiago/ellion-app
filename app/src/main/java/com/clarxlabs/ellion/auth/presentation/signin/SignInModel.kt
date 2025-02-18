package com.clarxlabs.ellion.auth.presentation.signin

import com.clarxlabs.ellion.application.config.NavRoute
import kotlinx.serialization.Serializable

sealed interface SignInModel {
    @Serializable
    data class State(
        val email: String = "deoliv.tiago@gmail.com",
        val emailError: String = "",
        val password: String = "4m1Mad?",
        val passwordError: String = "",
        val isPasswordVisible: Boolean = false,
        val isLoading: Boolean = false,
    ) : SignInModel

    sealed interface Event {
        data class OnEmailChanged(val email: String) : Event
        data class OnPasswordChanged(val password: String) : Event
        data object OnPasswordVisibilityClicked : Event
        data class OnSubmitClicked(val navigateTo: (NavRoute) -> Unit) : Event
        data object OnResetPasswordClicked : Event
        data object OnContactClicked : Event
        data object OnTermsClicked : Event
        data object OnPoliciesClicked : Event
        data class OnSignUpClicked(val navigateTo: (NavRoute) -> Unit) : Event
    }
}
