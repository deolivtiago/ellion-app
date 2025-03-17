package com.clarxlabs.ellion.auth.presentation.confirm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.clarxlabs.ellion.application.config.NavRoute
import com.clarxlabs.ellion.application.utilities.Either
import com.clarxlabs.ellion.auth.domain.services.AuthenticationService
import com.clarxlabs.ellion.auth.domain.services.AuthenticationService.Confirm
import com.clarxlabs.ellion.auth.domain.services.AuthenticationService.Verify
import com.clarxlabs.ellion.auth.presentation.AppViewModel
import kotlinx.coroutines.launch

class ConfirmViewModel(
    private val authenticationService: AuthenticationService,
    handle: SavedStateHandle,
) : AppViewModel<ConfirmModel.State, ConfirmModel.Event>(ConfirmModel.State(handle)) {

    override fun onEvent(event: ConfirmModel.Event) {
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
                    if (it is Either.Success) event.navigateTo(NavRoute.SignIn)
                }

                setState { it.copy(isLoading = false) }
            }

            is ConfirmModel.Event.OnCodeChanged -> {
                setState { it.copy(code = event.code, codeError = "") }
            }
        }
    }

    private fun sendVerificationEmail(onResponse: (Either<Verify.Output, Verify.Error>) -> Unit = {}) {
        val input = Verify.Input(email = state.value.email)

        viewModelScope.launch { onResponse(authenticationService.verify(input)) }
    }

    private fun confirmEmailVerification(onResponse: (Either<Confirm.Output, Confirm.Error>) -> Unit = {}) {
        val input = Confirm.Input(email = state.value.email, code = state.value.code)

        viewModelScope.launch { onResponse(authenticationService.confirm(input)) }
    }
}
