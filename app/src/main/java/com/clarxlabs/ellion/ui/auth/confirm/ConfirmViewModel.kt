package com.clarxlabs.ellion.ui.auth.confirm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.clarxlabs.ellion.core.repositories.AuthenticationRepository
import com.clarxlabs.ellion.core.repositories.types.ConfirmError
import com.clarxlabs.ellion.core.repositories.types.ConfirmInput
import com.clarxlabs.ellion.core.repositories.types.ConfirmOutput
import com.clarxlabs.ellion.core.repositories.types.VerifyError
import com.clarxlabs.ellion.core.repositories.types.VerifyInput
import com.clarxlabs.ellion.core.repositories.types.VerifyOutput
import com.clarxlabs.ellion.ui.AppRoute
import com.clarxlabs.ellion.ui.AppViewModel
import it.czerwinski.kotlin.util.Either
import kotlinx.coroutines.launch

class ConfirmViewModel(
    private val authenticationRepository: AuthenticationRepository,
    handle: SavedStateHandle,
) : AppViewModel<ConfirmModel.State, ConfirmModel.Event>(ConfirmModel.State(handle)) {

    override fun sendEvent(event: ConfirmModel.Event) {
        when (event) {
            is ConfirmModel.Event.OnCodeChanged -> onCodeChanged(event.code.trim())
            is ConfirmModel.Event.OnSubmitClicked -> onSubmitClicked(event.navigateTo)
            is ConfirmModel.Event.OnSendCodeClicked -> onSendCodeClicked()
            is ConfirmModel.Event.OnContactClicked -> onContactClicked(event.navigateTo)
        }
    }

    private fun onCodeChanged(text: String) {
        setState { it.copy(code = text, codeError = "") }
    }

    private fun onSendCodeClicked() {
        setState { it.copy(isLoading = true) }

        sendVerificationEmail()

        setState { it.copy(isLoading = false) }
    }

    private fun onSubmitClicked(navigateTo: (AppRoute) -> Unit) {
        setState { it.copy(isLoading = true) }

        confirmEmailVerification { if (it.isRight) navigateTo(AppRoute.SignIn) }

        setState { it.copy(isLoading = false) }
    }

    private fun onContactClicked(navigateTo: (AppRoute) -> Unit) {
        setState { it.copy(isLoading = false) }
    }

    private fun sendVerificationEmail(onResponse: (Either<VerifyError, VerifyOutput>) -> Unit = {}) {
        val input = VerifyInput(email = state.value.email)

        viewModelScope.launch { onResponse(authenticationRepository.verify(input)) }
    }

    private fun confirmEmailVerification(onResponse: (Either<ConfirmError, ConfirmOutput>) -> Unit) {
        val input = ConfirmInput(email = state.value.email, code = state.value.code)

        viewModelScope.launch { onResponse(authenticationRepository.confirm(input)) }
    }
}
