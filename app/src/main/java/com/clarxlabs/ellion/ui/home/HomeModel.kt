package com.clarxlabs.ellion.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.clarxlabs.ellion.ui.AppRoute
import com.clarxlabs.ellion.core.entities.User
import com.clarxlabs.ellion.ui.AppModel
import kotlinx.serialization.Serializable

sealed interface HomeModel : AppModel {
    @Serializable
    data class State(
        val accessToken: String = "jwt.access.token",
        val refreshToken: String = "jwt.refresh.token",

        val user: User = User(),

        val email: String = "deoliv.tiago@gmail.com",
        val emailError: String = "",
        val password: String = "4m1Mad?",
        val passwordError: String = "",

        val isLoading: Boolean = false,
    ) : AppModel.State {
        constructor(handle: SavedStateHandle) : this(
            accessToken = handle.toRoute<AppRoute.Home>().accessToken,
            refreshToken = handle.toRoute<AppRoute.Home>().refreshToken,
        )
    }

    sealed interface Event : AppModel.Event {
        data class OnAccessTokenChanged(val accessToken: String) : Event
        data class OnRefreshTokenChanged(val refreshToken: String) : Event
        data class OnSignOutClicked(val navigateTo: (AppRoute) -> Unit) : Event
        data class OnEmailChanged(val text: String) : Event
        data class OnSubmitClicked(val navigateTo: (AppRoute) -> Unit) : Event
        data class OnProfileClicked(val navigateTo: (AppRoute) -> Unit) : Event
    }
}
