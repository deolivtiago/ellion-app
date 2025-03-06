package com.clarxlabs.ellion.auth.presentation.verify

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.clarxlabs.ellion.application.config.NavRoute
import com.clarxlabs.ellion.auth.presentation.AppModel
import kotlinx.serialization.Serializable

sealed interface VerifyModel : AppModel {
    @Serializable
    data class State(
        val email: String = "invalid@mail.com",

        val isLoading: Boolean = false,
    ) : AppModel.State<VerifyModel> {
        constructor(handle: SavedStateHandle) : this(
            email = handle.toRoute<NavRoute.Confirm>().email
        )
    }

    sealed interface Event : AppModel.Event<VerifyModel> {
        data class OnSubmitClicked(val navigateTo: (NavRoute) -> Unit) : Event
        data object OnContactClicked : Event
        data class OnConfirmClicked(val navigateTo: (NavRoute) -> Unit) : Event
    }
}

