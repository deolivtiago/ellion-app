package com.clarxlabs.ellion.auth.presentation.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clarxlabs.ellion.application.config.NavRoute
import com.clarxlabs.ellion.application.defaults.MainHttpResponse
import com.clarxlabs.ellion.auth.data.remote.AuthDataSource
import com.clarxlabs.ellion.auth.data.remote.inputs.SignInInput
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(private val authDataSource: AuthDataSource) : ViewModel() {
    private val initialState = SignInModel.State()
    private val _state = MutableStateFlow(initialState)
    private val setState = _state::update
    val state = _state.asStateFlow()

    fun onEvent(event: SignInModel.Event) {
        when (event) {
            is SignInModel.Event.OnEmailChanged -> {
                setState { it.copy(email = event.email, emailError = "") }
            }

            is SignInModel.Event.OnPasswordChanged -> {
                setState { it.copy(password = event.password, passwordError = "") }
            }

            is SignInModel.Event.OnPasswordVisibilityClicked -> {
                setState { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            }

            is SignInModel.Event.OnResetPasswordClicked -> {
                setState { it.copy(isPasswordVisible = false) }
            }

            is SignInModel.Event.OnSignUpClicked -> {
                setState { it.copy(isPasswordVisible = false) }

                event.navigateTo(NavRoute.SignUp)
            }

            is SignInModel.Event.OnContactClicked -> {
                setState { it.copy(isLoading = false, isPasswordVisible = false) }
            }

            is SignInModel.Event.OnSubmitClicked -> {
                setState { it.copy(isLoading = true, isPasswordVisible = false) }

                signIn {
                    when (it.status.value) {
                        200 -> viewModelScope.launch {
                            val auth = it
                                .body<MainHttpResponse.OkResponse<Map<String, String>>>()
                                .data

                            event.navigateTo(
                                NavRoute.Home(
                                    auth["access_token"]!!,
                                    auth["refresh_token"]!!,
                                )
                            )
                        }

                        422 -> viewModelScope.launch {
                            val errors = it
                                .body<MainHttpResponse.UnprocessableEntity<Map<String, List<String>>>>()
                                .errors

                            if (errors.containsKey("email") &&
                                errors["email"]!!.contains("must be verified")
                            ) {
                                event.navigateTo(NavRoute.Verify(state.value.email))
                            }
                        }
                    }
                }

                setState { it.copy(isLoading = false) }
            }

            is SignInModel.Event.OnTermsClicked -> {
                setState { it.copy(isLoading = false, isPasswordVisible = false) }
            }

            is SignInModel.Event.OnPoliciesClicked -> {
                setState { it.copy(isLoading = false, isPasswordVisible = false) }
            }
        }
    }

    private fun signIn(onResponse: (HttpResponse) -> Unit) {
        val input = SignInInput(state.value.email, state.value.password)

        viewModelScope.launch { onResponse(authDataSource.signIn(input)) }
    }
}
