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
import com.clarxlabs.ellion.auth.presentation.components.FilledButton
import com.clarxlabs.ellion.auth.presentation.components.FormHeader
import com.clarxlabs.ellion.auth.presentation.components.QuestionButton
import com.clarxlabs.ellion.auth.presentation.components.SignInFormFields
import com.clarxlabs.ellion.auth.presentation.components.TermsAndPolicies
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignInView(
    viewModel: SignInViewModel = koinViewModel<SignInViewModel>(),
    onNavigate: (NavRoute) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent

    SignInViewContent(state, onEvent, onNavigate)
}

@Composable
fun SignInViewContent(
    state: SignInModelState,
    onEvent: (SignInModelEvent) -> Unit,
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
                FormHeader(title = "Acessar Cadastro")

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    QuestionButton(
                        questionText = "Precisa de ajuda?",
                        actionTitle = "Entrar em contato",
                        onClicked = { onEvent(SignInModelEvent.OnContactClicked) },
                        modifier = Modifier.align(Alignment.End),
                    )
                    QuestionButton(
                        questionText = "Esqueceu sua senha?",
                        actionTitle = "Recuperar",
                        onClicked = { onEvent(SignInModelEvent.OnResetPasswordClicked) },
                        modifier = Modifier.align(Alignment.End),
                        isEnabled = !state.isLoading,
                    )
                }

                SignInFormFields(
                    email = state.email,
                    emailErrorMessage = state.emailErrorMessage,
                    onEmailChanged = { onEvent(SignInModelEvent.OnEmailChanged(it)) },
                    password = state.password,
                    passwordErrorMessage = state.passwordErrorMessage,
                    onPasswordChanged = { onEvent(SignInModelEvent.OnPasswordChanged(it)) },
                    isPasswordVisible = state.isPasswordVisible,
                    onPasswordVisibilityClicked = { onEvent(SignInModelEvent.OnPasswordVisibilityClicked) },
                    isLoading = state.isLoading,
                )

                FilledButton(
                    onClicked = { onEvent(SignInModelEvent.OnSubmitClicked) },
                    isLoading = state.isLoading,
                )

                TermsAndPolicies(
                    onTermsClicked = { onEvent(SignInModelEvent.OnTermsClicked) },
                    onPoliciesClicked = { onEvent(SignInModelEvent.OnPoliciesClicked) }
                )

                QuestionButton(
                    questionText = "Não possui cadastro?",
                    actionTitle = "CADASTRAR",
                    onClicked = { onEvent(SignInModelEvent.OnSignUpClicked(onNavigate)) },
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
            state = SignInModelState(email = "alice@wonderland.co"),
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
            state = SignInModelState(email = "alice@wonderland.co"),
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
            state = SignInModelState(email = "alice@wonderland.co"),
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
            state = SignInModelState(email = "alice@wonderland.co"),
            onEvent = {},
            onNavigate = {},
        )
    }
}
