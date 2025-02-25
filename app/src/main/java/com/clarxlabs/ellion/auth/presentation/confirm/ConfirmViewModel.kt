package com.clarxlabs.ellion.auth.presentation.confirm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.clarxlabs.ellion.application.config.NavRoute
import com.clarxlabs.ellion.auth.data.remote.AuthDataSource
import com.clarxlabs.ellion.auth.data.remote.inputs.ConfirmInput
import com.clarxlabs.ellion.auth.data.remote.inputs.VerifyInput
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ConfirmViewModel(
    private val authDataSource: AuthDataSource,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val initialState = ConfirmModel.State(
        email = savedStateHandle.toRoute<NavRoute.Confirm>().email
    )
    private val _state = MutableStateFlow(initialState)
    private val setState = _state::update
    val state = _state.asStateFlow()

    fun onEvent(event: ConfirmModel.Event) {
        when (event) {
            is ConfirmModel.Event.OnContactClicked -> {
                setState { it.copy(isLoading = false) }
            }

            is ConfirmModel.Event.OnSendCodeClicked -> {
                setState { it.copy(isLoading = true) }

                sendVerificationEmail()

                setState { it.copy(isLoading = false) }
            }

            is ConfirmModel.Event.OnSubmitClicked -> {
                setState { it.copy(isLoading = true) }

                confirmEmailVerification {
                    if (it.status.value == 200) event.navigateTo(NavRoute.SignIn)
                }

                setState { it.copy(isLoading = false) }
            }

            is ConfirmModel.Event.OnCodeChanged -> {
                setState { it.copy(code = event.code, codeError = "") }
            }
        }
    }

    private fun sendVerificationEmail(onResponse: (HttpResponse) -> Unit = {}) {
        val input = VerifyInput(email = state.value.email)

        viewModelScope.launch { onResponse(authDataSource.verify(input)) }
    }

    private fun confirmEmailVerification(onResponse: (HttpResponse) -> Unit = {}) {
        val input = ConfirmInput(email = state.value.email, code = state.value.code)

        viewModelScope.launch { onResponse(authDataSource.confirm(input)) }
    }
}
