package com.clarxlabs.ellion.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.clarxlabs.ellion.ui.theme.EllionTheme

@Composable
fun TextFormField(
    modifier: Modifier = Modifier,
    value: String = "",
    valueErrorMessage: String = "",
    onValueChanged: (String) -> Unit = {},
    label: String="Email",
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    leadingIcon: @Composable (() -> Unit) = {
        Icon(
            imageVector = Icons.Default.Email,
            contentDescription = "Email icon"
        )
    },
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        isError = valueErrorMessage.isNotEmpty(),
        supportingText = { Text(text = valueErrorMessage).takeUnless { valueErrorMessage.isEmpty() } },
        label = { Text(label) },
        shape = MaterialTheme.shapes.large,
        enabled = !isLoading and isEnabled,
        leadingIcon = leadingIcon,
        maxLines = 1,
        modifier = modifier.fillMaxWidth(),
    )
}

@Preview
@Composable
private fun TextFormFieldPreview() {
    EllionTheme { Surface { TextFormField() } }
}
