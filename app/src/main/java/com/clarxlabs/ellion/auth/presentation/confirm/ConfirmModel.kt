package com.clarxlabs.ellion.auth.presentation.confirm

import com.clarxlabs.ellion.application.config.NavRoute
import kotlinx.serialization.Serializable
import kotlin.String

sealed interface ConfirmModel {
    @Serializable
    data class State(
        val email: String = "invalid@mail.com",
        val code: String = "",
        val codeError: String = "",
        val isLoading: Boolean = false,
    ) : ConfirmModel

    sealed interface Event {
        data class OnCodeChanged(val code: String) : Event
        data class OnSubmitClicked(val navigateTo: (NavRoute) -> Unit) : Event
        data object OnContactClicked : Event
        data class OnSendCodeClicked(val navigateTo: (NavRoute) -> Unit) : Event
    }
}
