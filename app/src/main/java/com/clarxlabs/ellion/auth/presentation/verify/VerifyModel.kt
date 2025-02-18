package com.clarxlabs.ellion.auth.presentation.verify

import com.clarxlabs.ellion.application.config.NavRoute
import kotlinx.serialization.Serializable
import kotlin.String

sealed interface VerifyModel {
    @Serializable
    data class State(
        val email: String = "invalid@mail.com",
        val isLoading: Boolean = false,
    ) : VerifyModel

    sealed interface Event {
        data class OnSubmitClicked(val navigateTo: (NavRoute) -> Unit) : Event
        data object OnContactClicked : Event
        data class OnConfirmClicked(val navigateTo: (NavRoute) -> Unit) : Event
    }
}

