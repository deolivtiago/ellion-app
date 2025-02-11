package com.clarxlabs.ellion.auth.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clarxlabs.ellion.ui.theme.EllionTheme

@Composable
fun TermsAndPolicies(
    onTermsClicked: () -> Unit = {},
    onPoliciesClicked: () -> Unit = {},
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Center,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
) {
    Column(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Ao continuar, você está aceitando nossos",
            style = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center)
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextButton(
                modifier = Modifier.height(28.dp),
                onClick = onTermsClicked,
                contentPadding = PaddingValues(vertical = 0.dp, horizontal = 4.dp),
                shape = MaterialTheme.shapes.small,
            ) {
                Text(
                    text = "Termos de Uso",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                    ),
                )
            }
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(0.dp),
                text = "e",
                style = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center),
            )
            TextButton(
                modifier = Modifier.height(28.dp),
                onClick = onPoliciesClicked,
                contentPadding = PaddingValues(vertical = 0.dp, horizontal = 4.dp),
                shape = MaterialTheme.shapes.small,
            ) {
                Text(
                    text = "Política de Privacidade",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                )
            }
        }
    }
}

@Preview()
@Composable
fun TermsAndPoliciesPreview() {
    EllionTheme {
        Surface {
            TermsAndPolicies()
        }
    }
}
