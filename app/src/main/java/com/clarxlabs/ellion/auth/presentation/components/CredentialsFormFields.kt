package com.clarxlabs.ellion.auth.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.clarxlabs.ellion.application.theme.EllionTheme

@Composable
fun CredentialsFormFields(
    modifier: Modifier = Modifier,
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
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChanged,
            isError = emailErrorMessage.isNotEmpty(),
            supportingText = { Text(text = emailErrorMessage).takeUnless { emailErrorMessage.isEmpty() } },
            label = { Text("Email") },
            shape = MaterialTheme.shapes.large,
            enabled = !isLoading and isEnabled,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = "Email icon")
            },
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChanged,
            isError = passwordErrorMessage.isNotEmpty(),
            supportingText = { Text(text = passwordErrorMessage).takeUnless { passwordErrorMessage.isEmpty() } },
            label = { Text("Senha") },
            shape = MaterialTheme.shapes.large,
            enabled = !isLoading and isEnabled,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = "Password icon")
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
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(),
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
