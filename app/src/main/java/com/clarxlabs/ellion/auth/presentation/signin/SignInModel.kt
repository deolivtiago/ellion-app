package com.clarxlabs.ellion.auth.presentation.signin

import com.clarxlabs.ellion.application.config.NavRoute
import com.clarxlabs.ellion.auth.presentation.AppModel
import kotlinx.serialization.Serializable

sealed interface SignInModel : AppModel {
    @Serializable
    data class State(
        val email: String = "deoliv.tiago@gmail.com",
        val password: String = "4m1Mad?",

        val emailError: String = "",
        val passwordError: String = "",

        val isPasswordVisible: Boolean = false,
        val isFormValid: Boolean = false,

        val isLoading: Boolean = false,
    ) : AppModel.State

    sealed interface Event : AppModel.Event {
        data class OnEmailChanged(val email: String) : Event
        data class OnPasswordChanged(val password: String) : Event
        data object OnPasswordVisibilityClicked : Event
        data class OnSubmitClicked(val navigateTo: (NavRoute) -> Unit) : Event
        data object OnResetPasswordClicked : Event
        data object OnContactClicked : Event
        data class OnTermsClicked(val navigateTo: (NavRoute) -> Unit) : Event
        data class OnPoliciesClicked(val navigateTo: (NavRoute) -> Unit) : Event
        data class OnSignUpClicked(val navigateTo: (NavRoute) -> Unit) : Event
    }
}
