package com.clarxlabs.ellion.auth.presentation.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.clarxlabs.ellion.application.config.NavRoute
import com.clarxlabs.ellion.auth.data.remote.AuthDataSource
import com.clarxlabs.ellion.auth.data.remote.dtos.SignOutInput
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val authDataSource: AuthDataSource,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val initialState = HomeModel.State(
        accessToken = savedStateHandle.toRoute<NavRoute.Home>().accessToken,
        refreshToken = savedStateHandle.toRoute<NavRoute.Home>().refreshToken,
    )
    private val _state = MutableStateFlow(initialState)
    private val setState = _state::update
    val state = _state.asStateFlow()

    fun onEvent(event: HomeModel.Event) {
        when (event) {
            is HomeModel.Event.OnAccessTokenChanged -> {
                setState { it.copy(accessToken = event.accessToken) }
            }

            is HomeModel.Event.OnRefreshTokenChanged -> {
                setState { it.copy(refreshToken = event.refreshToken) }
            }

            is HomeModel.Event.OnSignOutClicked -> {
                setState { it.copy(isLoading = true) }

                signOut {
                    if (it.status.value == 204) {
                        setState { it.copy(accessToken = "", refreshToken = "") }

                        event.navigateTo(NavRoute.SignIn)
                    }
                }

                setState { it.copy(isLoading = false) }
            }
        }
    }

    private fun signOut(onResponse: (HttpResponse) -> Unit) {
        val input = SignOutInput(state.value.accessToken, state.value.refreshToken)

        viewModelScope.launch { onResponse(authDataSource.signOut(input)) }
    }
}
