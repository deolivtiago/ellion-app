package com.clarxlabs.ellion.auth.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.clarxlabs.ellion.auth.ui.components.BottomLink
import com.clarxlabs.ellion.auth.ui.components.TermsAndPolicies


@Composable
fun SignInView(modifier: Modifier = Modifier) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .imePadding(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
//                        .background(MaterialTheme.colorScheme.primaryContainer)
                    .clip(RoundedCornerShape(bottomEnd = 128.dp))
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.primary),
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .clip(RoundedCornerShape(topStart = 16.dp))
                    .background(MaterialTheme.colorScheme.background),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
//                        .navigationBarsPadding()
                        .padding(top = 48.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
                    verticalArrangement = Arrangement.Bottom,

                    ) {
//                    Box(
//                        modifier = Modifier
//                            .background(Color.Green)
//                            .size(32.dp)
//                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
//                        Text(
////                                modifier = Modifier.padding(vertical = 2.dp),
//                            text = "Please, enter your name and email to",
//                            style = MaterialTheme.typography.bodyLarge,
//                        )

                        Text(
                            text = "Access your account",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                        )


                        TextButton(
                            modifier = Modifier.align(Alignment.End),
                            onClick = {}) {
                            Text(
                                text = "Password recovery",
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }

                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            value = "",
                            onValueChange = {},
                            label = { Text("Email") },
                        )
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            value = "",
                            onValueChange = {},
                            label = { Text("Password") },
                        )

                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            onClick = {},
                        ) {
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = "NEXT",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }

                    TermsAndPolicies(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    )

                    BottomLink(modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(), onClick = {})
                }
            }
        }
    }
}

@Composable
fun SplashView(modifier: Modifier = Modifier) {
    Surface(modifier = modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .clip(RoundedCornerShape(bottomEnd = 64.dp))
                        .background(MaterialTheme.colorScheme.tertiaryContainer)
                        .weight(1f),
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.tertiaryContainer)
                        .clip(RoundedCornerShape(topStart = 64.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .weight(1f),
                )
            }

            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Button(
                    onClick = {}, elevation = ButtonDefaults.elevatedButtonElevation(),
//                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ) { Text(text = "SIGN IN") }
                Button(
                    onClick = {}, elevation = ButtonDefaults.elevatedButtonElevation(),
//                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
//                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                ) { Text(text = "SIGN UP") }
            }
        }
    }
}
