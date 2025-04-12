package com.clarxlabs.ellion.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.clarxlabs.ellion.ui.AppRoute
import com.clarxlabs.ellion.core.repositories.AuthenticationRepository
import com.clarxlabs.ellion.core.repositories.types.SignOutError
import com.clarxlabs.ellion.core.repositories.types.SignOutInput
import com.clarxlabs.ellion.core.repositories.types.SignOutOutput
import com.clarxlabs.ellion.core.repositories.types.UserInfoError
import com.clarxlabs.ellion.core.repositories.types.UserInfoInput
import com.clarxlabs.ellion.core.repositories.types.UserInfoOutput
import com.clarxlabs.ellion.ui.AppViewModel
import it.czerwinski.kotlin.util.Either
import kotlinx.coroutines.launch

class HomeViewModel(
    private val authenticationRepository: AuthenticationRepository,
    handle: SavedStateHandle,
) : AppViewModel<HomeModel.State, HomeModel.Event>(HomeModel.State(handle)) {

    init {
        userInfo {
            it.fold(
                { setState { it.copy(user = it.user.copy(fullName = "error")) } },
                { user -> setState { it.copy(user = user) } }
            )
        }
    }

    override fun sendEvent(event: HomeModel.Event) {
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
                    if (it.isRight) {
                        setState { it.copy(accessToken = "", refreshToken = "") }

                        event.navigateTo(AppRoute.SignIn)
                    }
                }

                setState { it.copy(isLoading = false) }
            }

            is HomeModel.Event.OnEmailChanged -> {
                setState { it.copy(email = event.text) }
            }

            is HomeModel.Event.OnSubmitClicked -> {}
            is HomeModel.Event.OnProfileClicked -> onProfileClicked(event.navigateTo)
        }
    }

    private fun onProfileClicked(navigateTo: (AppRoute) -> Unit) {
        navigateTo(AppRoute.Profile(userId = state.value.user.id))
    }

    private fun signOut(onResponse: (Either<SignOutError, SignOutOutput>) -> Unit) {
        val input = SignOutInput(state.value.accessToken, state.value.refreshToken)

        viewModelScope.launch { onResponse(authenticationRepository.signOut(input)) }
    }

    private fun userInfo(onResponse: (Either<UserInfoError, UserInfoOutput>) -> Unit = {}) {
        val input = UserInfoInput(state.value.accessToken, state.value.refreshToken)

        viewModelScope.launch { onResponse(authenticationRepository.userInfo(input)) }
    }
}

