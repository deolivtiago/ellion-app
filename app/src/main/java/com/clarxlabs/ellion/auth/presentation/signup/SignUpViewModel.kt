package com.clarxlabs.ellion.auth.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clarxlabs.ellion.application.config.NavRoute
import com.clarxlabs.ellion.auth.data.remote.AuthDataSource
import com.clarxlabs.ellion.auth.data.remote.dtos.SignUpInput
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(private val authDataSource: AuthDataSource) : ViewModel() {
    private val _state = MutableStateFlow(SignUpModelState())
    val state = _state.asStateFlow()

    fun onEvent(event: SignUpModelEvent) {
        when (event) {
            is SignUpModelEvent.OnContactClicked -> {
                _state.update { it.copy(isLoading = false, isPasswordVisible = false) }
            }

            is SignUpModelEvent.OnEmailChanged -> {
                _state.update { it.copy(email = event.email, emailErrorMessage = event.email) }
            }

            is SignUpModelEvent.OnFullNameChanged -> {
                _state.update {
                    it.copy(
                        fullName = event.fullName,
                        fullNameErrorMessage = event.fullName
                    )
                }
            }

            is SignUpModelEvent.OnPasswordChanged -> {
                _state.update {
                    it.copy(
                        password = event.password,
                        passwordErrorMessage = event.password
                    )
                }
            }

            is SignUpModelEvent.OnPasswordConfirmationChanged -> {
                _state.update {
                    it.copy(
                        passwordConfirmation = event.passwordConfirmation,
                        passwordConfirmationErrorMessage = event.passwordConfirmation
                    )
                }
            }

            is SignUpModelEvent.OnPasswordVisibilityClicked -> {
                _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            }

            is SignUpModelEvent.OnPoliciesClicked -> {
                _state.update { it.copy(isPasswordVisible = false) }
            }

            is SignUpModelEvent.OnResetPasswordClicked -> {
                _state.update { it.copy(isPasswordVisible = false) }
            }

            is SignUpModelEvent.OnSignInClicked -> {
                _state.update { it.copy(isPasswordVisible = false) }

                event.navigateTo(NavRoute.SignIn)
            }

            is SignUpModelEvent.OnSubmitClicked -> {
                _state.update { it.copy(isLoading = true, isPasswordVisible = false) }

                val input = SignUpInput(
                    firstName = state.value.fullName.substringBefore(" "),
                    lastName = state.value.fullName.substringAfter(" "),
                    email = state.value.email,
                    password = state.value.password,
                )

                viewModelScope.launch { authDataSource.signUp(input) }
            }

            is SignUpModelEvent.OnTermsClicked -> {
                _state.update { it.copy(isPasswordVisible = false) }
            }
        }
    }
}
