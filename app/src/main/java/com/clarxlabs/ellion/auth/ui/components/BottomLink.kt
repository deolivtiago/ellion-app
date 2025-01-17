package com.clarxlabs.ellion.auth.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clarxlabs.ellion.ui.theme.EllionTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BottomLink(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    actionDescription: String = "Don't you have an account?",
    actionTitle: String = "SIGN UP",
    horizontalArrangement: Arrangement.HorizontalOrVertical = Arrangement.Center,
    verticalArrangement: Arrangement.Vertical = Arrangement.Center
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement,
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(horizontal = 8.dp),
            text = actionDescription,
            style = MaterialTheme.typography.titleMedium,
        )
        TextButton(onClick = onClick) {
            Text(
                text = actionTitle,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )

        }
    }

}

@Preview
@Composable
fun BottomLinkPreview() {
    EllionTheme {
        Surface {
            BottomLink(onClick = {})
        }
    }
}
