package com.clarxlabs.ellion.auth.presentation.confirm

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.clarxlabs.ellion.application.config.NavRoute
import com.clarxlabs.ellion.auth.presentation.AppModel
import kotlinx.serialization.Serializable

sealed interface ConfirmModel : AppModel {
    @Serializable
    data class State(
        val email: String = "invalid@mail.com",
        val code: String = "",

        val codeError: String = "",

        val isLoading: Boolean = false,
    ) : AppModel.State<ConfirmModel> {
        constructor(handle: SavedStateHandle) : this(
            email = handle.toRoute<NavRoute.Confirm>().email
        )
    }

    sealed interface Event : AppModel.Event<ConfirmModel> {
        data class OnCodeChanged(val code: String) : Event
        data class OnSubmitClicked(val navigateTo: (NavRoute) -> Unit) : Event
        data object OnContactClicked : Event
        data class OnSendCodeClicked(val navigateTo: (NavRoute) -> Unit) : Event
    }
}
