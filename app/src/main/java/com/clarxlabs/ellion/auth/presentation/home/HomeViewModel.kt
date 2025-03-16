package com.clarxlabs.ellion.auth.presentation.home

import android.util.Patterns
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.clarxlabs.ellion.application.config.NavRoute
import com.clarxlabs.ellion.auth.data.remote.AuthDataSource
import com.clarxlabs.ellion.auth.data.remote.inputs.SignOutInput
import com.clarxlabs.ellion.auth.presentation.AppViewModel
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.launch

class HomeViewModel(
    private val authDataSource: AuthDataSource,
    handle: SavedStateHandle,
) : AppViewModel<HomeModel.State, HomeModel.Event>(HomeModel.State(handle)) {

    override fun onEvent(event: HomeModel.Event) {
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

            is HomeModel.Event.OnEmailChanged -> {
//                setState { it.copy(input = it.input.copy(email = event.text)) }
//                setState { it.copy(error = it.input.emailError()) }

                setState { it.copy(email = event.text) }
            }

            is HomeModel.Event.OnPasswordChanged -> {
//                setState {
//                    it.copy(
//                        input = it.input.copy(password = event.text),
//                        error = it.input.passwordError(),
//                    )
//                }
                setState { it.copy(password = event.text) }
            }

            is HomeModel.Event.OnSubmitClicked -> {}
        }
    }

    private fun validate() {

        setState {
            it.copy(
                emailError = if (state.value.email.isEmpty()) "Campo obrigatório" else "",
                passwordError = if (state.value.password.isEmpty()) "Campo obrigatório" else ""
            )
        }

    }

    private fun validateEmail(text: String) =
        Patterns.EMAIL_ADDRESS.matcher(text).matches().let {
            when (it) {
                true -> ValidationError.VALID
                false -> ValidationError.INVALID_FORMAT
            }
        }

    private fun errorMessage(error: ValidationError): String {
        val validationMessages = mapOf<ValidationError, String>(
            ValidationError.INVALID_FORMAT.to("Formato inválido"),
        )

        return error.let { validationMessages.getOrDefault(it, "") }
    }


    private fun signOut(onResponse: (HttpResponse) -> Unit) {
        val input = SignOutInput(state.value.accessToken, state.value.refreshToken)

        viewModelScope.launch { onResponse(authDataSource.signOut(input)) }
    }
}

enum class ValidationError { INVALID_FORMAT, VALID }
