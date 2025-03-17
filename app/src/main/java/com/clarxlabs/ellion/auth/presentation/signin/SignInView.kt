package com.clarxlabs.ellion.auth.presentation.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.clarxlabs.ellion.application.config.NavRoute
import com.clarxlabs.ellion.application.theme.EllionTheme
import com.clarxlabs.ellion.auth.presentation.components.ActionButton
import com.clarxlabs.ellion.auth.presentation.components.CredentialsFormFields
import com.clarxlabs.ellion.auth.presentation.components.FormHeader
import com.clarxlabs.ellion.auth.presentation.components.QuestionButton
import com.clarxlabs.ellion.auth.presentation.components.TermsAndPolicies

@Composable
fun SignInView(viewModel: SignInViewModel, onNavigate: (NavRoute) -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent

    SignInViewContent(state, onEvent, onNavigate)
}

@Composable
fun SignInViewContent(
    state: SignInModel.State,
    onEvent: (SignInModel.Event) -> Unit,
    onNavigate: (NavRoute) -> Unit,
) {
    Surface(
        modifier = Modifier
            .navigationBarsPadding()
            .fillMaxSize(),
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                .imePadding(),
        ) {
            Spacer(
                modifier = Modifier
                    .widthIn(max = 480.dp)
                    .fillMaxSize()
                    .weight(3f)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .widthIn(max = 480.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .clip(
                        RoundedCornerShape(
                            topEnd = 32.dp,
                            bottomStart = 32.dp,
                            topStart = 4.dp,
                            bottomEnd = 4.dp,
                        )
                    )
                    .background(MaterialTheme.colorScheme.background)
                    .padding(bottom = 16.dp, top = 32.dp, start = 16.dp, end = 16.dp),
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    FormHeader(title = "Acessar Cadastro")

                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        QuestionButton(
                            questionText = "Precisa de ajuda?",
                            actionTitle = "Entrar em contato",
                            onClicked = { onEvent(SignInModel.Event.OnContactClicked) },
                            modifier = Modifier.align(Alignment.End),
                        )
                        QuestionButton(
                            questionText = "Esqueceu sua senha?",
                            actionTitle = "Recuperar",
                            onClicked = { onEvent(SignInModel.Event.OnResetPasswordClicked) },
                            modifier = Modifier.align(Alignment.End),
                            isEnabled = !state.isLoading,
                        )
                    }
                }

                Column {
                    CredentialsFormFields(
                        email = state.email,
                        emailErrorMessage = state.emailError,
                        onEmailChanged = { onEvent(SignInModel.Event.OnEmailChanged(it)) },
                        password = state.password,
                        passwordErrorMessage = state.passwordError,
                        onPasswordChanged = { onEvent(SignInModel.Event.OnPasswordChanged(it)) },
                        isPasswordVisible = state.isPasswordVisible,
                        onPasswordVisibilityClicked = { onEvent(SignInModel.Event.OnPasswordVisibilityClicked) },
                        isLoading = state.isLoading,
                    )

                    ActionButton(
                        modifier = Modifier.padding(vertical = 8.dp),
                        onClicked = { onEvent(SignInModel.Event.OnSubmitClicked(onNavigate)) },
                        isLoading = state.isLoading,
                        isEnabled = state.isFormValid,
                    )
                }

                TermsAndPolicies(
                    onTermsClicked = { onEvent(SignInModel.Event.OnTermsClicked(onNavigate)) },
                    onPoliciesClicked = { onEvent(SignInModel.Event.OnPoliciesClicked(onNavigate)) }
                )

                QuestionButton(
                    questionText = "Não possui cadastro?",
                    actionTitle = "CADASTRAR",
                    onClicked = { onEvent(SignInModel.Event.OnSignUpClicked(onNavigate)) },
                    isEnabled = !state.isLoading,
                )
            }
            Spacer(
                modifier = Modifier
                    .widthIn(max = 480.dp)
                    .fillMaxSize()
                    .weight(1f)
            )
        }
    }
}

@Preview(showSystemUi = true, device = "spec:parent=pixel_3a")
@Composable
fun PreviewPhone() {
    EllionTheme {
        SignInViewContent(
            state = SignInModel.State(email = "alice@wonderland.co"),
            onEvent = {},
            onNavigate = {},
        )
    }
}

@Preview(showSystemUi = true, device = "spec:parent=Galaxy Nexus,navigation=buttons")
@Composable
fun PreviewPhoneSmall() {
    EllionTheme {
        SignInViewContent(
            state = SignInModel.State(email = "alice@wonderland.co"),
            onEvent = {},
            onNavigate = {},
        )
    }
}

@Preview(device = "spec:parent=Nexus 7 2013,navigation=buttons", showSystemUi = true)
@Composable
fun PreviewTabletPortrait() {
    EllionTheme {
        SignInViewContent(
            state = SignInModel.State(email = "alice@wonderland.co"),
            onEvent = {},
            onNavigate = {},
        )
    }
}

@Preview(device = "spec:parent=Nexus 10,navigation=buttons", showSystemUi = true)
@Composable
fun PreviewTabletLandscape() {
    EllionTheme {
        SignInViewContent(
            state = SignInModel.State(email = "alice@wonderland.co"),
            onEvent = {},
            onNavigate = {},
        )
    }
}
