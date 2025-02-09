package com.clarxlabs.ellion.views

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.util.TableInfo
import com.clarxlabs.ellion.auth.presentation.components.QuestionButton
import com.clarxlabs.ellion.auth.presentation.components.TermsAndPolicies
import com.clarxlabs.ellion.ui.theme.EllionTheme
import com.clarxlabs.ellion.views.components.FilledButton
import com.clarxlabs.ellion.views.components.FormHeader
import org.w3c.dom.Text


@Composable
fun CodeVerificationViewContent() {
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
                            topStart = 4.dp,
                            topEnd = 64.dp,
                            bottomStart = 64.dp,
                            bottomEnd = 4.dp,
                        )
                    )
                    .background(MaterialTheme.colorScheme.background)
                    .padding(bottom = 32.dp, start = 16.dp, end = 16.dp, top = 64.dp),
            ) {
                FormHeader(title = "Verifique seu email")


                QuestionButton(
                    questionText = "Precisa de ajuda?",
                    actionTitle = "Entrar em contato",
                    modifier = Modifier.align(Alignment.End),
                )

                Column {
                    Text(text = "Enviamos um código de confirmação para")
                    Text(
                        text = "invalid@mail.com",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                        )
                    )
                    Text(text = "Por favor, verifique seu email e informe o código recebido.")
                }

                OutlinedTextField(
                   value = "",
                    onValueChange = {},

                )

                FilledButton()

                QuestionButton(
                    questionText = "Não recebeu o código?",
                    actionTitle = "REENVIAR"
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
fun CodeVerificationPreviewPhone() {
    EllionTheme {
        CodeVerificationViewContent()
    }
}

@Preview(showSystemUi = true, device = "spec:parent=Galaxy Nexus,navigation=buttons")
@Composable
fun CodeVerificationPreviewPhoneSmall() {
    EllionTheme {
        CodeVerificationViewContent()
    }
}

@Preview(device = "spec:parent=Nexus 7 2013,navigation=buttons", showSystemUi = true)
@Composable
fun CodeVerificationPreviewTabletPortrait() {
    EllionTheme {
        CodeVerificationViewContent()
    }
}

@Preview(device = "spec:parent=Nexus 10,navigation=buttons", showSystemUi = true)
@Composable
fun CodeVerificationPreviewTabletLandscape() {
    EllionTheme {
        CodeVerificationViewContent()
    }
}