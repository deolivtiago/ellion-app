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
        val passwordConfirmationError: String = "",

        val isPasswordVisible: Boolean = false,

        val isLoading: Boolean = false,
    ) : AppModel.State {
        fun firstName(): String =
            fullName.replace("  ", " ").substringBefore(" ").trim()
                .replaceFirstChar { it.uppercase() }

        fun lastName(): String =
            fullName.replace("  ", " ").substringAfter(" ", "").trimStart().split(" ")
                .joinToString(" ") { if (it.length > 2) it.replaceFirstChar { it.uppercase() } else it }
    }

    sealed interface Event : AppModel.Event {
        data class OnEmailChanged(val text: String) : Event
        data class OnFullNameChanged(val text: String) : Event
        data class OnPasswordChanged(val text: String) : Event
        data class OnPasswordConfirmationChanged(val text: String) : Event
        data class OnSubmitClicked(val navigateTo: (NavRoute) -> Unit) : Event
        data class OnPasswordVisibilityClicked(val navigateTo: (NavRoute) -> Unit) : Event
        data class OnResetPasswordClicked(val navigateTo: (NavRoute) -> Unit) : Event
        data class OnContactClicked(val navigateTo: (NavRoute) -> Unit) : Event
        data class OnSignInClicked(val navigateTo: (NavRoute) -> Unit) : Event
        data class OnTermsClicked(val navigateTo: (NavRoute) -> Unit) : Event
        data class OnPoliciesClicked(val navigateTo: (NavRoute) -> Unit) : Event
    }
}
