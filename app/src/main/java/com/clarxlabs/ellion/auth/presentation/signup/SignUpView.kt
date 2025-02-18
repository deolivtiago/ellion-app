package com.clarxlabs.ellion.auth.presentation.signup

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
fun SignUpView(viewModel: SignUpViewModel, onNavigate: (NavRoute) -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent

    SignUpViewContent(state, onEvent, onNavigate)
}

@Composable
fun SignUpViewContent(
    state: SignUpModel.State,
    onEvent: (SignUpModel.Event) -> Unit,
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
                    FormHeader(title = "Criar Cadastro")

                    QuestionButton(
                        questionText = "Precisa de ajuda?",
                        actionTitle = "Entrar em contato",
                        onClicked = { onEvent(SignUpModel.Event.OnContactClicked) },
                        modifier = Modifier.align(Alignment.End),
                    )
                }

                Column {
                    OutlinedTextField(
                        value = state.fullName,
                        onValueChange = { onEvent(SignUpModel.Event.OnFullNameChanged(it)) },
                        label = { Text("Nome Completo") },
                        shape = MaterialTheme.shapes.large,
                        enabled = !state.isLoading,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Full name icon"
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp),
                    )

                    CredentialsFormFields(
                        email = state.email,
                        onEmailChanged = { onEvent(SignUpModel.Event.OnEmailChanged(it)) },
                        password = state.password,
                        onPasswordChanged = { onEvent(SignUpModel.Event.OnPasswordChanged(it)) },
                        isPasswordVisible = state.isPasswordVisible,
                        onPasswordVisibilityClicked = { onEvent(SignUpModel.Event.OnPasswordVisibilityClicked) },
                        isLoading = state.isLoading,
                    )

                    OutlinedTextField(
                        value = state.passwordConfirmation,
                        onValueChange = { onEvent(SignUpModel.Event.OnPasswordConfirmationChanged(it)) },
                        label = { Text("Confirmação de Senha") },
                        shape = MaterialTheme.shapes.large,
                        enabled = !state.isLoading,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Password confirmation icon"
                            )
                        },
                        visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            IconButton(
                                onClick = { onEvent(SignUpModel.Event.OnPasswordVisibilityClicked) },
                                enabled = !state.isLoading,
                            ) {
                                Icon(
                                    imageVector = if (state.isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = "Password visibility toggle",
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp),
                    )
                }

                ActionButton(
                    onClicked = { onEvent(SignUpModel.Event.OnSubmitClicked(onNavigate)) },
                    isLoading = state.isLoading,
                )

                TermsAndPolicies(
                    onTermsClicked = { onEvent(SignUpModel.Event.OnTermsClicked) },
                    onPoliciesClicked = { onEvent(SignUpModel.Event.OnPoliciesClicked) },
                )

                QuestionButton(
                    questionText = "Já possui cadastro?",
                    actionTitle = "ENTRAR",
                    onClicked = { onEvent(SignUpModel.Event.OnSignInClicked(onNavigate)) },
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
        SignUpViewContent(
            state = SignUpModel.State(),
            onEvent = {},
            onNavigate = {},
        )
    }
}

@Preview(showSystemUi = true, device = "spec:parent=Galaxy Nexus,navigation=buttons")
@Composable
fun PreviewPhoneSmall() {
    EllionTheme {
        SignUpViewContent(
            state = SignUpModel.State(),
            onEvent = {},
            onNavigate = {},
        )
    }
}

@Preview(device = "spec:parent=Nexus 7 2013,navigation=buttons", showSystemUi = true)
@Composable
fun PreviewTabletPortrait() {
    EllionTheme {
        SignUpViewContent(
            state = SignUpModel.State(),
            onEvent = {},
            onNavigate = {},
        )
    }
}

@Preview(device = "spec:parent=Nexus 10,navigation=buttons", showSystemUi = true)
@Composable
fun PreviewTabletLandscape() {
    EllionTheme {
        SignUpViewContent(
            state = SignUpModel.State(),
            onEvent = {},
            onNavigate = {},
        )
    }
}
