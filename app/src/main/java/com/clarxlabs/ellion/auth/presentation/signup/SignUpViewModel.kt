package com.clarxlabs.ellion.auth.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clarxlabs.ellion.application.config.NavRoute
import com.clarxlabs.ellion.auth.data.remote.AuthDataSource
import com.clarxlabs.ellion.auth.data.remote.dtos.SignUpInput
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(private val authDataSource: AuthDataSource) : ViewModel() {
    private val initialState = SignUpModel.State()
    private val _state = MutableStateFlow(initialState)
    private val setState = _state::update
    val state = _state.asStateFlow()

    fun onEvent(event: SignUpModel.Event) {
        when (event) {
            is SignUpModel.Event.OnContactClicked -> {
                setState { it.copy(isLoading = false, isPasswordVisible = false) }
            }

            is SignUpModel.Event.OnEmailChanged -> {
                setState { it.copy(email = event.email, emailError = "") }
            }

            is SignUpModel.Event.OnFullNameChanged -> {
                setState { it.copy(fullName = event.fullName, fullNameError = "") }
            }

            is SignUpModel.Event.OnPasswordChanged -> {
                setState { it.copy(password = event.password, passwordError = "") }
            }

            is SignUpModel.Event.OnPasswordConfirmationChanged -> {
                setState {
                    it.copy(
                        passwordConfirmation = event.passwordConfirmation,
                        passwordConfirmationError = ""
                    )
                }
            }

            is SignUpModel.Event.OnPasswordVisibilityClicked -> {
                setState { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            }

            is SignUpModel.Event.OnPoliciesClicked -> {
                setState { it.copy(isPasswordVisible = false) }
            }

            is SignUpModel.Event.OnResetPasswordClicked -> {
                setState { it.copy(isPasswordVisible = false) }
            }

            is SignUpModel.Event.OnSignInClicked -> {
                setState { it.copy(isPasswordVisible = false) }

                event.navigateTo(NavRoute.SignIn)
            }

            is SignUpModel.Event.OnSubmitClicked -> {
                setState { it.copy(isLoading = true, isPasswordVisible = false) }

                signUp {
                    if (it.status.value == 201) {
                        event.navigateTo(NavRoute.Verify(state.value.email))
                    }
                }

                setState { it.copy(isLoading = false) }
            }

            is SignUpModel.Event.OnTermsClicked -> {
                setState { it.copy(isPasswordVisible = false) }
            }
        }
    }

    private fun signUp(onResponse: (HttpResponse) -> Unit) {
        val input = SignUpInput(
            firstName = state.value.fullName.substringBefore(" "),
            lastName = state.value.fullName.substringAfter(" "),
            email = state.value.email,
            password = state.value.password,
        )

        viewModelScope.launch { onResponse(authDataSource.signUp(input)) }
    }
}
