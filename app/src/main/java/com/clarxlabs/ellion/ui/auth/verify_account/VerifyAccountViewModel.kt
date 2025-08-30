package com.clarxlabs.ellion.ui.auth.verify_account

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

class VerifyAccountViewModel(
    private val authenticationRepository: AuthenticationRepository,
    handle: SavedStateHandle,
) : AppViewModel<VerifyAccountModel.State, VerifyAccountModel.Event>(VerifyAccountModel.State(handle)) {

    override fun sendEvent(event: VerifyAccountModel.Event) {
        when (event) {
            is VerifyAccountModel.Event.OnSubmitClicked -> onSubmitClicked(event.navigateTo)
            is VerifyAccountModel.Event.OnContactClicked -> onContactClicked(event.navigateTo)
            is VerifyAccountModel.Event.OnConfirmClicked -> onConfirmedClicked(event.navigateTo)
        }
    }

    private fun onSubmitClicked(navigateTo: (AppRoute) -> Unit) {
        setState { it.copy(isLoading = true) }

        sendVerificationEmail { navigateTo(AppRoute.ConfirmAccount(email = state.value.email)) }

        setState { it.copy(isLoading = false) }
    }

    private fun onConfirmedClicked(navigateTo: (AppRoute) -> Unit) {
        when (state.value.verificationType) {
            VerificationType.CONFIRM_ACCOUNT ->
                navigateTo(AppRoute.ConfirmAccount(email = state.value.email))

            VerificationType.RESET_PASSWORD ->
                navigateTo(AppRoute.ResetPassword(email = state.value.email))

            VerificationType.CHANGE_EMAIL -> TODO()
        }
    }

    private fun onContactClicked(navigateTo: (AppRoute) -> Unit) {
        setState { it.copy(isLoading = false) }
    }

    private fun sendVerificationEmail(onResponse: (Either<VerifyError, VerifyOutput>) -> Unit = {}) {
        val input = VerifyInput(email = state.value.email)

        viewModelScope.launch { onResponse(authenticationRepository.verify(input)) }
    }
}
