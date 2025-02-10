package com.clarxlabs.ellion

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import com.clarxlabs.ellion.todos.data.remote.UserApiService
import com.clarxlabs.ellion.ui.theme.EllionTheme
import com.clarxlabs.ellion.views.SignUpViewContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.compose.KoinApplication

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MainApp() }
    }
}

@Composable
fun MainApp() {
    KoinApplication(
        application = { modules() }
    ) {
        EllionTheme {
//                SignInView(viewModel = SignInViewModel())
            SignUpViewContent()
        }
    }
}

@Composable
fun SignInViewContent() {
    Surface(
        modifier = Modifier
            .navigationBarsPadding()
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                .imePadding(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(
                modifier = Modifier
                    .widthIn(max = 480.dp)
                    .fillMaxSize()
                    .weight(3f)
            )
            Column(
                modifier = Modifier
                    .widthIn(max = 480.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
//                    .clip(MaterialTheme.shapes.large)
//                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 64.dp))
                    .clip(
                        RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 64.dp,
                            bottomStart = 32.dp,
                            bottomEnd = 4.dp,
                        )
                    )
                    .background(MaterialTheme.colorScheme.background)
                    .padding(bottom = 32.dp, start = 16.dp, end = 16.dp, top = 64.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Acesse sua conta",
                        style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.SemiBold)
                    )
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    QuestionButton(
                        modifier = Modifier.align(Alignment.End),
                        questionText = "",
                        actionTitle = "Precisa de ajuda?",
                    )
                    QuestionButton(
                        modifier = Modifier.align(Alignment.End),
                        questionText = "Esqueceu a senha?",
                        actionTitle = "RECUPERAR",
                    )
                }
                Column {
                    TextField(
                        shape = MaterialTheme.shapes.extraSmall,

                        value = "",
                        onValueChange = { },
                        label = { Text("Email") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp),
                    )

                    OutlinedTextField(
                        shape = MaterialTheme.shapes.large,
                        value = "",
                        onValueChange = { },
                        label = { Text("Senha") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp),
                    )
                }

                Button(
                    shape = MaterialTheme.shapes.large,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            val users = UserApiService().listUsers()
                            Log.d(
                                ":::", "onCreate: $users"
                            )
                        }
                    },
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "PRÓXIMO",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }
                TermsAndPolicies()
                QuestionButton(
                    questionText = "Não possui cadastro?",
                    actionTitle = "CADASTRAR"
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
        SignInViewContent()
    }
}

@Preview(showSystemUi = true, device = "spec:parent=Galaxy Nexus,navigation=buttons")
@Composable
fun PreviewPhoneSmall() {
    EllionTheme {
        SignInViewContent()
    }
}

@Preview(device = "spec:parent=Nexus 7 2013,navigation=buttons", showSystemUi = true)
@Composable
fun PreviewTabletPortrait() {
    EllionTheme {
        SignInViewContent()
    }
}

@Preview(device = "spec:parent=Nexus 10,navigation=buttons", showSystemUi = true)
@Composable
fun PreviewTabletLandscape() {
    EllionTheme {
        SignInViewContent()
    }
}