package com.clarxlabs.ellion.auth.presentation.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clarxlabs.ellion.application.config.NavRoute
import com.clarxlabs.ellion.auth.data.remote.AuthDataSource
import com.clarxlabs.ellion.auth.data.remote.dtos.SignInInput
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(private val authDataSource: AuthDataSource) : ViewModel() {
    private val _state = MutableStateFlow(SignInModelState())
    val state = _state.asStateFlow()

    fun onEvent(event: SignInModelEvent) {
        when (event) {
            is SignInModelEvent.OnEmailChanged -> {
                _state.update { it.copy(email = event.email, emailErrorMessage = event.email) }
            }

            is SignInModelEvent.OnPasswordChanged -> {
                _state.update {
                    it.copy(
                        password = event.password,
                        passwordErrorMessage = event.password
                    )
                }
            }

            is SignInModelEvent.OnPasswordVisibilityClicked -> {
                _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            }

            is SignInModelEvent.OnResetPasswordClicked -> {
                _state.update { it.copy(isPasswordVisible = false) }
            }

            is SignInModelEvent.OnSignUpClicked -> {
                _state.update {
                    it.copy(isPasswordVisible = false)
                }

                event.navigateTo(NavRoute.SignUp)
            }

            is SignInModelEvent.OnContactClicked -> {
                _state.update { it.copy(isLoading = false, isPasswordVisible = false) }
            }

            is SignInModelEvent.OnSubmitClicked -> {
                _state.update { it.copy(isLoading = true, isPasswordVisible = false) }

                val input = SignInInput(state.value.email, state.value.password)

                viewModelScope.launch { authDataSource.signIn(input) }
            }

            is SignInModelEvent.OnTermsClicked -> {
                _state.update {
                    it.copy(isLoading = false, isPasswordVisible = false, emailErrorMessage = "")
                }
            }

            is SignInModelEvent.OnPoliciesClicked -> {
                _state.update {
                    it.copy(isLoading = false, isPasswordVisible = false, passwordErrorMessage = "")
                }
            }

        }
    }
}
