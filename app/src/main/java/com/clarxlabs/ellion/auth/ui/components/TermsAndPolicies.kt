package com.clarxlabs.ellion.auth.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TermsAndPolicies(
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
            text = "By clicking in next, you agree to our",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        FlowRow(
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Center,
        ) {
            TextButton(
                modifier = Modifier.height(28.dp),
                onClick = {},
                contentPadding = PaddingValues(vertical = 0.dp, horizontal = 4.dp),
            ) {
                Text(
                    text = "Terms of Use",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = "and",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
            )
            TextButton(
                modifier = Modifier.height(28.dp),
                onClick = {},
                contentPadding = PaddingValues(vertical = 0.dp, horizontal = 4.dp),

                ) {
                Text(
                    text = "Privacy Policy",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
//    }
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
