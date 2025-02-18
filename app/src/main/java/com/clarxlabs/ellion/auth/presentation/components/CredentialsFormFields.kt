package com.clarxlabs.ellion.auth.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clarxlabs.ellion.application.theme.EllionTheme

@Composable
fun CredentialsFormFields(
    email: String = "",
    emailErrorMessage: String = "",
    onEmailChanged: (String) -> Unit = {},
    password: String = "",
    passwordErrorMessage: String = "",
    onPasswordChanged: (String) -> Unit = {},
    isPasswordVisible: Boolean = false,
    onPasswordVisibilityClicked: () -> Unit = {},
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChanged,
            isError = emailErrorMessage.isNotEmpty(),
            supportingText = if (emailErrorMessage.isNotEmpty()) {
                { Text(text = emailErrorMessage) }
            } else null,
            label = { Text("Email") },
            shape = MaterialTheme.shapes.large,
            enabled = !isLoading and isEnabled,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email icon"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp),
        )
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChanged,
            isError = passwordErrorMessage.isNotEmpty(),
            supportingText = if (passwordErrorMessage.isNotEmpty()) {
                { Text(text = passwordErrorMessage) }
            } else null,
            label = { Text("Senha") },
            shape = MaterialTheme.shapes.large,
            enabled = !isLoading and isEnabled,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Password icon"
                )
            },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(
                    onClick = onPasswordVisibilityClicked,
                    enabled = !isLoading and isEnabled,
                ) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Password visibility toggle",
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp),
        )
    }
}

@Preview
@Composable
private fun SignInFormFieldsPreview() {
    EllionTheme {
        Surface {
            CredentialsFormFields()
        }
    }
}
