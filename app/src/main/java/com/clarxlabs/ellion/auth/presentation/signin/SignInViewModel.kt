package com.clarxlabs.ellion.auth.presentation.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clarxlabs.ellion.application.config.NavRoute
import com.clarxlabs.ellion.application.utilities.Result
import com.clarxlabs.ellion.auth.data.remote.AuthDataSource
import com.clarxlabs.ellion.auth.data.remote.dtos.CredentialsData
import com.clarxlabs.ellion.auth.data.remote.dtos.CredentialsError
import com.clarxlabs.ellion.auth.data.remote.dtos.TokensData
import com.clarxlabs.ellion.auth.domain.validation.ValidationComposite
import com.clarxlabs.ellion.auth.domain.validation.ValidationStrategy
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
                setState { it.copy(isLoading = true) }

                val validationResult = ValidationComposite.validate(
                    mapOf(
                        ValidationStrategy.Type.EMAIL to state.value.email,
                        ValidationStrategy.Type.PASSWORD to state.value.password,
                    )
                )

                interface TextValidatorResult
                interface TextValidator {
                    fun validate(text: String): TextValidatorResult
                }

                data class TextFieldType(val label: String = "", validators: List<TextValidator>)
                data class TextFieldStateMessage(
                    val isError: Boolean = false,
                    val message: String = ""
                )

                data class TextFieldState<T : TextFieldType>(
                    val type: T,
                    val value: String,
                    val message: TextFieldStateMessage = TextFieldStateMessage()
                )

                if (validationResult[ValidationStrategy.Type.EMAIL] != ValidationStrategy.Result.VALID)
                    setState { it.copy(emailError = "Email inválido") }
                else if (validationResult[ValidationStrategy.Type.PASSWORD] != ValidationStrategy.Result.VALID)
                    setState { it.copy(passwordError = "Senha inválida") }
                else
                    signIn {
                        when (it) {
                            is Result.Data -> event.navigateTo(
                                NavRoute.Home(
                                    accessToken = it.data.accessToken,
                                    refreshToken = it.data.refreshToken,
                                )
                            )

                            is Result.Error -> {
                                if (it.errors.email.contains("must be verified"))
                                    event.navigateTo(NavRoute.Verify(email = state.value.email))
                                else
                                    setState { state ->
                                        state.copy(
                                            emailError = it.errors.email.firstOrNull() ?: "",
                                            passwordError = it.errors.password.firstOrNull() ?: "",
                                        )
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

    private fun signIn(onResponse: (Result<TokensData, CredentialsError>) -> Unit) {
        val input = CredentialsData(
            email = state.value.email,
            password = state.value.password
        )

        viewModelScope.launch { onResponse(authDataSource.signIn(input)) }
    }
}
