package com.clarxlabs.ellion.ui.auth.verify

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.clarxlabs.ellion.ui.AppModel
import com.clarxlabs.ellion.ui.AppRoute
import kotlinx.serialization.Serializable

sealed interface VerifyModel : AppModel {
    @Serializable
    data class State(
        val email: String = "invalid@mail.com",

        val isLoading: Boolean = false,
    ) : AppModel.State {
        constructor(handle: SavedStateHandle) : this(
            email = handle.toRoute<AppRoute.Confirm>().email
        )
    }

    sealed interface Event : AppModel.Event {
        data class OnSubmitClicked(val navigateTo: (AppRoute) -> Unit) : Event
        data class OnContactClicked(val navigateTo: (AppRoute) -> Unit) : Event
        data class OnConfirmClicked(val navigateTo: (AppRoute) -> Unit) : Event
    }
}

