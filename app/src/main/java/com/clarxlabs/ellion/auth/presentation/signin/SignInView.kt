package com.clarxlabs.ellion.auth.presentation.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clarxlabs.ellion.auth.presentation.components.QuestionButton
import com.clarxlabs.ellion.auth.presentation.components.TermsAndPolicies
import com.clarxlabs.ellion.ui.theme.EllionTheme

@Composable
fun SignInView(viewModel: SignInViewModel) {
    val state by viewModel.state.collectAsState()
    val onEvent = viewModel::onEvent

    SignInViewContent(state, onEvent)
}

@Composable
fun SignInViewContent(
    state: SignInModelState,
    onEvent: (SignInModelEvent) -> Unit,
) {

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .imePadding()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
//                        .background(MaterialTheme.colorScheme.primaryContainer)
                    .clip(RoundedCornerShape(bottomEnd = 128.dp))
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.primary),
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .clip(RoundedCornerShape(topStart = 16.dp))
                    .background(MaterialTheme.colorScheme.background),
            ) {
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier
                        .fillMaxSize()
//                        .navigationBarsPadding()
                        .padding(top = 32.dp, bottom = 0.dp, start = 16.dp, end = 16.dp),

                    ) {
//                    Box(
//                        modifier = Modifier
//                            .background(Color.Green)
//                            .size(32.dp)
//                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    ) {
//                        Text(
////                                modifier = Modifier.padding(vertical = 2.dp),
//                            text = "Please, enter your name and email to",
//                            style = MaterialTheme.typography.bodyLarge,
//                        )

                        Text(
                            text = "Access your account",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                        )


                        TextButton(
                            onClick = { onEvent(SignInModelEvent.OnResetPasswordClicked(state.email)) },
                            modifier = Modifier.align(Alignment.End),
                        ) {
                            Text(
                                text = "Password recovery",
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }

                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        TextField(
                            value = state.email,
                            onValueChange = { onEvent(SignInModelEvent.OnEmailChanged(it)) },
                            label = { Text("Email") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                        )
                        TextField(
                            value = state.password,
                            onValueChange = { onEvent(SignInModelEvent.OnPasswordChanged(it)) },
                            label = { Text("Password") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                        )

                        Button(
                            onClick = {
                                onEvent(
                                    SignInModelEvent.OnSubmitClicked(
                                        state.email,
                                        state.password
                                    )
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                        ) {
                            Text(
                                text = "NEXT",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(8.dp),
                            )
                        }
                    }

                    TermsAndPolicies(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                    )

                    QuestionButton(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        onClick = { onEvent(SignInModelEvent.OnSignUpClicked(state.email)) },
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun SignInViewPreview() {
    EllionTheme {
        SignInViewContent(SignInModelState(), {})
    }
}


@Composable
fun SplashView(modifier: Modifier = Modifier) {
    Surface(modifier = modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .clip(RoundedCornerShape(bottomEnd = 64.dp))
                        .background(MaterialTheme.colorScheme.tertiaryContainer)
                        .weight(1f),
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.tertiaryContainer)
                        .clip(RoundedCornerShape(topStart = 64.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .weight(1f),
                )
            }

            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Button(
                    onClick = {}, elevation = ButtonDefaults.elevatedButtonElevation(),
//                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ) { Text(text = "SIGN IN") }
                Button(
                    onClick = {}, elevation = ButtonDefaults.elevatedButtonElevation(),
//                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
//                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                ) { Text(text = "SIGN UP") }
            }
        }
    }
}

