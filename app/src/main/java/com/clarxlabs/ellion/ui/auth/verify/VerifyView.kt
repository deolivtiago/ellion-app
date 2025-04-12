package com.clarxlabs.ellion.ui.auth.verify

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.clarxlabs.ellion.ui.AppRoute
import com.clarxlabs.ellion.ui.components.ActionButton
import com.clarxlabs.ellion.ui.components.FormHeader
import com.clarxlabs.ellion.ui.components.QuestionButton
import com.clarxlabs.ellion.ui.theme.EllionTheme

@Composable
fun VerifyView(viewModel: VerifyViewModel, onNavigate: (AppRoute) -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::sendEvent

    VerifyViewContent(state, onEvent, onNavigate)
}

@Composable
fun VerifyViewContent(
    state: VerifyModel.State,
    onEvent: (VerifyModel.Event) -> Unit,
    onNavigate: (AppRoute) -> Unit,
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
                    FormHeader(title = "Verificar Email")

                    QuestionButton(
                        questionText = "Precisa de ajuda?",
                        actionTitle = "Entrar em contato",
                        onClicked = { onEvent(VerifyModel.Event.OnContactClicked(onNavigate)) },
                        modifier = Modifier.align(Alignment.End),
                    )
                }

                Text(
                    textAlign = TextAlign.Center,
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                                color = MaterialTheme.typography.bodyLarge.color,
                            )
                        ) { append("Antes de acessar seu cadastro\nprecisamos verificar o email\n") }
                        withStyle(
                            SpanStyle(
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.primary,
                                letterSpacing = TextUnit(1.5F, TextUnitType.Sp),
                            )
                        ) { append(state.email) }
                        withStyle(
                            SpanStyle(
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                                color = MaterialTheme.typography.bodyLarge.color,
                            )
                        ) { append("\nAo continuar, você receberá um email\ncom o código para confirmação\ndo seu cadastro.") }
                    }
                )

                ActionButton(
                    onClicked = { onEvent(VerifyModel.Event.OnSubmitClicked(onNavigate)) },
                    isLoading = state.isLoading,
                    actionTitle = "CONTINUAR"
                )

                QuestionButton(
                    questionText = "Já possui um código?",
                    actionTitle = "CONFIRMAR",
                    onClicked = { onEvent(VerifyModel.Event.OnConfirmClicked(onNavigate)) },
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

@Preview(device = "spec:parent=small_phone,navigation=buttons", showSystemUi = true)
@Composable
private fun VerifyViewContentPreview() {
    EllionTheme {
        VerifyViewContent(
            state = VerifyModel.State(),
            onEvent = {},
            onNavigate = {},
        )
    }
}
