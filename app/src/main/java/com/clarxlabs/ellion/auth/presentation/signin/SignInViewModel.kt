package com.clarxlabs.ellion.auth.presentation.signin

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel : ViewModel() {
    private val _state = MutableStateFlow(SignInModelState())
    val state = _state.asStateFlow()

    fun onEvent(event: SignInModelEvent) {
        when (event) {
            is SignInModelEvent.OnEmailChanged -> {
                _state.update { it.copy(event.email) }
            }

            is SignInModelEvent.OnPasswordChanged -> {
                _state.update { it.copy(event.password) }
            }

            is SignInModelEvent.OnResetPasswordClicked -> {
               _state.update { it.copy(event.email) }
            }

            is SignInModelEvent.OnSignUpClicked -> {
               _state.update { it.copy(event.email) }
            }

            is SignInModelEvent.OnSubmitClicked -> {
                _state.update { it.copy(event.email, event.password)
                }
            }
        }
    }
}