package com.clarxlabs.ellion.auth.presentation.signup

import com.clarxlabs.ellion.application.config.NavRoute
import com.clarxlabs.ellion.auth.presentation.AppModel
import kotlinx.serialization.Serializable

sealed interface SignUpModel : AppModel {
    @Serializable
    data class State(
        val email: String = "deoliv.tiago@gmail.com",
        val password: String = "4m1Mad?",
        val fullName: String = "Tiago de Oliveira",
        val passwordConfirmation: String = "4m1Mad??",

        val emailError: String = "",
        val passwordError: String = "",
        val fullNameError: String = "",
        val passwordConfirmationError: String = "As senhas devem ser iguais",

        val isPasswordVisible: Boolean = false,

        val isLoading: Boolean = false,
    ) : AppModel.State<SignUpModel>

    sealed interface Event : AppModel.Event<SignUpModel> {
        data class OnEmailChanged(val email: String) : Event
        data class OnFullNameChanged(val fullName: String) : Event
        data class OnPasswordChanged(val password: String) : Event
        data class OnPasswordConfirmationChanged(val passwordConfirmation: String) : Event
        data class OnSubmitClicked(val navigateTo: (NavRoute) -> Unit) : Event
        data object OnPasswordVisibilityClicked : Event
        data object OnResetPasswordClicked : Event
        data object OnContactClicked : Event
        data class OnSignInClicked(val navigateTo: (NavRoute) -> Unit) : Event
        data object OnTermsClicked : Event
        data object OnPoliciesClicked : Event
    }
}
