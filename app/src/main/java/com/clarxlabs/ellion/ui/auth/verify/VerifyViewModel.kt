package com.clarxlabs.ellion.ui.auth.verify

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.clarxlabs.ellion.core.repositories.AuthenticationRepository
import com.clarxlabs.ellion.core.repositories.types.VerifyError
import com.clarxlabs.ellion.core.repositories.types.VerifyInput
import com.clarxlabs.ellion.core.repositories.types.VerifyOutput
import com.clarxlabs.ellion.ui.AppRoute
import com.clarxlabs.ellion.ui.AppViewModel
import it.czerwinski.kotlin.util.Either
import kotlinx.coroutines.launch

class VerifyViewModel(
    private val authenticationRepository: AuthenticationRepository,
    handle: SavedStateHandle,
) : AppViewModel<VerifyModel.State, VerifyModel.Event>(VerifyModel.State(handle)) {

    override fun sendEvent(event: VerifyModel.Event) {
        when (event) {
            is VerifyModel.Event.OnSubmitClicked -> onSubmitClicked(event.navigateTo)
            is VerifyModel.Event.OnContactClicked -> onContactClicked(event.navigateTo)
            is VerifyModel.Event.OnConfirmClicked -> onConfirmedClicked(event.navigateTo)
        }
    }

    private fun onSubmitClicked(navigateTo: (AppRoute) -> Unit) {
        setState { it.copy(isLoading = true) }

        sendVerificationEmail { navigateTo(AppRoute.Confirm(email = state.value.email)) }

        setState { it.copy(isLoading = false) }
    }

    private fun onConfirmedClicked(navigateTo: (AppRoute) -> Unit) {
        navigateTo(AppRoute.Confirm(email = state.value.email))
    }

    private fun onContactClicked(navigateTo: (AppRoute) -> Unit) {
        setState { it.copy(isLoading = false) }
    }

    private fun sendVerificationEmail(onResponse: (Either<VerifyError, VerifyOutput>) -> Unit = {}) {
        val input = VerifyInput(email = state.value.email)

        viewModelScope.launch { onResponse(authenticationRepository.verify(input)) }
    }
}
