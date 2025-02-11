package com.clarxlabs.ellion.auth.signin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clarxlabs.ellion.auth.signin.data.RemoteDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(
    private val remoteDataSource: RemoteDataSource,
) : ViewModel() {
    private val _state = MutableStateFlow(SignInModelState())
    val state = _state.asStateFlow()

    fun onEvent(event: SignInModelEvent) {
        when (event) {
            is SignInModelEvent.OnEmailChanged -> {
                _state.update { it.copy(email = event.email) }
            }

            is SignInModelEvent.OnPasswordChanged -> {
                _state.update { it.copy(password = event.password) }
            }

            is SignInModelEvent.OnPasswordVisibilityClicked -> {
                _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            }

            is SignInModelEvent.OnResetPasswordClicked -> {
                _state.update { it.copy(isPasswordVisible = false) }
            }

            is SignInModelEvent.OnSignUpClicked -> {
                _state.update { it.copy(isPasswordVisible = false) }
            }

            is SignInModelEvent.OnContactClicked -> {
                _state.update { it.copy(isLoading = false, isPasswordVisible = false) }
            }

            is SignInModelEvent.OnSubmitClicked -> {
                _state.update { it.copy(isLoading = true, isPasswordVisible = false) }

                viewModelScope.launch {
                    val u = remoteDataSource.signIn()
                    Log.d("::", u.toString())
                }
            }

        }
    }
}
