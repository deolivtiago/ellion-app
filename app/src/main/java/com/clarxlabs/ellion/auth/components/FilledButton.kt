package com.clarxlabs.ellion.auth.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clarxlabs.ellion.ui.theme.EllionTheme

@Composable
fun FilledButton(
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    onClicked: () -> Unit = {},
    actionTitle: String = "PRÓXIMO",
    modifier: Modifier = Modifier
) {
    Button(
        enabled = !isLoading and isEnabled,
        shape = MaterialTheme.shapes.large,
        modifier = modifier.fillMaxWidth(),
        onClick = onClicked,
    ) {
        if (isLoading) CircularProgressIndicator()
        else Text(
            text = actionTitle,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(8.dp),
        )
    }
}

@Preview
@Composable
private fun FilledButtonPreview() {
    EllionTheme {
        Surface { FilledButton() }
    }
}
