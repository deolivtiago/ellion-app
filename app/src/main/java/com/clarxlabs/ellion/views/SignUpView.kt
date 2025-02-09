package com.clarxlabs.ellion.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clarxlabs.ellion.auth.presentation.components.QuestionButton
import com.clarxlabs.ellion.auth.presentation.components.TermsAndPolicies
import com.clarxlabs.ellion.ui.theme.EllionTheme
import com.clarxlabs.ellion.views.components.FilledButton
import com.clarxlabs.ellion.views.components.FormHeader
import com.clarxlabs.ellion.views.components.SignUpFormFields


@Composable
fun SignUpView() {
    SignUpViewContent()
}

@Composable
fun SignUpViewContent() {
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
                FormHeader(title = "Criar nova conta")

                QuestionButton(
                    questionText = "Precisa de ajuda?",
                    actionTitle = "Entrar em contato",
                    modifier = Modifier.align(Alignment.End),
                )

                SignUpFormFields()

                FilledButton()

                TermsAndPolicies()

                QuestionButton(
                    questionText = "Já possui cadastro?",
                    actionTitle = "ENTRAR"
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
fun SignUpPreviewPhone() {
    EllionTheme {
        SignUpViewContent()
    }
}

@Preview(showSystemUi = true, device = "spec:parent=Galaxy Nexus,navigation=buttons")
@Composable
fun SignUpPreviewPhoneSmall() {
    EllionTheme {
        SignUpViewContent()
    }
}

@Preview(device = "spec:parent=Nexus 7 2013,navigation=buttons", showSystemUi = true)
@Composable
fun SignUpPreviewTabletPortrait() {
    EllionTheme {
        SignUpViewContent()
    }
}

@Preview(device = "spec:parent=Nexus 10,navigation=buttons", showSystemUi = true)
@Composable
fun SignUpPreviewTabletLandscape() {
    EllionTheme {
        SignUpViewContent()
    }
}