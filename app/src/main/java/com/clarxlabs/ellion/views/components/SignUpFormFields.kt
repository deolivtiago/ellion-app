package com.clarxlabs.ellion.views.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clarxlabs.ellion.ui.theme.EllionTheme

@Composable
fun SignUpFormFields(
    fullName: String = "",
    onFullNameChanged: (String) -> Unit = {},
    email: String = "",
    onEmailChanged: (String) -> Unit = {},
    password: String = "",
    onPasswordChanged: (String) -> Unit = {},
    passwordConfirmation: String = "",
    onPasswordConfirmationChanged: (String) -> Unit = {},

    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = fullName,
            onValueChange = onFullNameChanged,
            label = { Text("Nome Completo") },
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp),
        )

        OutlinedTextField(
            value = email,
            onValueChange = onEmailChanged,
            label = { Text("Email") },
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp),
        )

        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChanged,
            label = { Text("Senha") },
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp),
        )

        OutlinedTextField(
            value = passwordConfirmation,
            onValueChange = onPasswordConfirmationChanged,
            label = { Text("Confirmação de senha") },
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp),
        )
    }
}

@Preview
@Composable
private fun SignUpFormFieldsPreview() {
    EllionTheme {
        Surface {
            SignUpFormFields()
        }
    }
}