package com.clarxlabs.ellion.auth.presentation.signin

import androidx.lifecycle.viewModelScope
import com.clarxlabs.ellion.application.config.NavRoute
import com.clarxlabs.ellion.application.utilities.Either
import com.clarxlabs.ellion.auth.domain.services.AuthenticationService
import com.clarxlabs.ellion.auth.domain.services.AuthenticationService.SignIn
import com.clarxlabs.ellion.auth.domain.validation.TextValidator
import com.clarxlabs.ellion.auth.domain.validation.validators.EmailValidator
import com.clarxlabs.ellion.auth.domain.validation.validators.LengthValidator
import com.clarxlabs.ellion.auth.domain.validation.validators.RegexValidator
import com.clarxlabs.ellion.auth.presentation.AppViewModel
import com.clarxlabs.ellion.auth.presentation.components.FieldValidator
import kotlinx.coroutines.launch

enum class TextFieldType(val validators: List<TextValidator> = emptyList()) {
    EMAIL(listOf(LengthValidator(), EmailValidator())),
    PASSWORD(listOf(LengthValidator(), RegexValidator(Regex("\\d+"))))
}

data class TextFieldMessage(
    val message: String = "",
    val isError: Boolean = false,
)

interface TextFieldIdentifier

data class TextFieldState(
    val id: TextFieldIdentifier,
    val value: String,
    val validator: TextValidator,
    val type: TextFieldType,
    val message: TextFieldMessage = TextFieldMessage()
)

class SignInViewModel(
    private val authService: AuthenticationService,
) : AppViewModel<SignInModel.State, SignInModel.Event>(SignInModel.State()) {

    fun mapErrorMessage(result: TextValidator.Result): String {
        return when (result) {
            TextValidator.Result.INVALID_FORMAT -> "Formato inválido"
            TextValidator.Result.TOO_SHORT -> "Muito curto"
            TextValidator.Result.TOO_LONG -> "Muito longo"
            else -> ""
        }
    }

    override fun onEvent(event: SignInModel.Event) {
        when (event) {
            is SignInModel.Event.OnEmailChanged -> {
                setState {
                    it.copy(
                        email = event.email,
                        emailError = FieldValidator
                            .Email()
                            .validate(event.email)
                            .let(::mapErrorMessage)
                    )
                }
            }

            is SignInModel.Event.OnPasswordChanged -> {
                setState {
                    it.copy(
                        password = event.password,
                        passwordError = FieldValidator
                            .Password()
                            .validate(event.password)
                            .let(::mapErrorMessage)
                    )
                }
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

                signIn { result ->
                    when (result) {
                        is Either.Success -> event.navigateTo(
                            NavRoute.Home(
                                accessToken = result.output.accessToken,
                                refreshToken = result.output.refreshToken,
                            )
                        )

                        is Either.Error -> {
                            if (result.output.email == "must be verified")
                                event.navigateTo(NavRoute.Verify(state.value.email))
                            else
                                setState {
                                    it.copy(
                                        emailError = result.output.email,
                                        passwordError = result.output.password
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

    private fun signIn(onResponse: (Either<SignIn.Output, SignIn.Error>) -> Unit) {
        val input = SignIn.Input(
            email = state.value.email,
            password = state.value.password
        )

        viewModelScope.launch { onResponse(authService.signIn(input)) }
    }
}
