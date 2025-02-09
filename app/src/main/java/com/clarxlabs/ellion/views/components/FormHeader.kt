package com.clarxlabs.ellion.views.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.clarxlabs.ellion.ui.theme.EllionTheme

@Composable
fun FormHeader(
    title: String = "Criar nova conta",
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme
                .typography
                .headlineLarge
                .copy(fontWeight = FontWeight.SemiBold)
        )
    }
}

@Preview
@Composable
private fun FormHeaderPreview() {
    EllionTheme {
        Surface {
            FormHeader()
        }
    }
}