package com.clarxlabs.ellion.auth.presentation.verify

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.clarxlabs.ellion.application.config.NavRoute
import com.clarxlabs.ellion.auth.data.remote.AuthDataSource
import com.clarxlabs.ellion.auth.data.remote.inputs.VerifyInput
import com.clarxlabs.ellion.auth.presentation.AppViewModel
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.launch

class VerifyViewModel(
    private val authDataSource: AuthDataSource,
    handle: SavedStateHandle,
) : AppViewModel<VerifyModel.State, VerifyModel.Event>(VerifyModel.State(handle)) {

    override fun onEvent(event: VerifyModel.Event) {
        when (event) {
            is VerifyModel.Event.OnContactClicked -> {
                setState { it.copy(isLoading = false) }
            }

            is VerifyModel.Event.OnConfirmClicked -> {
                event.navigateTo(NavRoute.Confirm(email = state.value.email))
            }

            is VerifyModel.Event.OnSubmitClicked -> {
                setState { it.copy(isLoading = true) }

                sendVerificationEmail { event.navigateTo(NavRoute.Confirm(email = state.value.email)) }

                setState { it.copy(isLoading = false) }
            }
        }
    }

    private fun sendVerificationEmail(onResponse: (HttpResponse) -> Unit = {}) {
        val input = VerifyInput(email = state.value.email)

        viewModelScope.launch { onResponse(authDataSource.verify(input)) }
    }
}
