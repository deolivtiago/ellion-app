package com.clarxlabs.ellion.viewstesting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun SignInView(modifier: Modifier = Modifier) {
    Surface(modifier = modifier
        .fillMaxSize().navigationBarsPadding(),
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        listOf(
                            MaterialTheme.colorScheme.tertiary,
                            MaterialTheme.colorScheme.primary,
                        ),
                    ),
                )
                .verticalScroll(rememberScrollState())
                .imePadding(),
            verticalArrangement = Arrangement.Bottom
        ) {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .weight(1f),
//            ) { Text(text = "ok") }
            SignInForm()

        }
    }
}


@Composable
fun SignUpView(modifier: Modifier = Modifier) {
    Surface(modifier = modifier
        .fillMaxSize().navigationBarsPadding(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        listOf(
                            MaterialTheme.colorScheme.tertiary,
                            MaterialTheme.colorScheme.primary,
                        ),
                    ),
                )
                .verticalScroll(rememberScrollState())
                .imePadding(),
            verticalArrangement = Arrangement.Bottom
        ) {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .weight(1f),
//            ) { Text(text = "ok") }
            SignUpForm()

        }
    }
}

@Composable
fun SignUpForm(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 32.dp))
            .background(Color.White)
            .padding(top = 48.dp, bottom = 16.dp, start = 32.dp, end = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Title(supportText = "Please, enter your information to", title = "Create a new Account")
        SignUpFields()
        TermsAndPolicies()
        BottomLink(onClick = {}, text = "Do you have an account?", label = "SIGN IN")
    }
}

@Composable
fun SignInForm(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 32.dp))
            .background(Color.White)
            .padding(top = 48.dp, bottom = 16.dp, start = 32.dp, end = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Title()
        SignInFields()
        TermsAndPolicies()
        BottomLink(onClick = {})
    }
}

@Composable
fun Title(
    modifier: Modifier = Modifier,
    title: String = "Access your account",
    supportText: String = "Please, enter your credentials to"
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = supportText,
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun SignInFields(modifier: Modifier = Modifier) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = "",
            label = { Text("Email") },
            onValueChange = { },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email Icon"
                )
            },
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = "",
            label = { Text("Password") },
            onValueChange = { },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Lock Icon"
                )
            },
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .height(64.dp), contentAlignment = Alignment.Center
        ) {
            ElevatedButton(
                modifier = Modifier.fillMaxSize(),
                onClick = { },
                shape = MaterialTheme.shapes.small,
            ) {
                Text(
                    text = "NEXT",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,

                    )

            }
        }
    }
}

@Composable
fun TermsAndPolicies(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = "By clicking in next, you agree to our",
            style = MaterialTheme.typography.titleMedium,
        )
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = "Terms of Use",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "and",
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = "Privacy Policy",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
fun BottomLink(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String = "Don't you have an account?",
    label: String = "SIGN UP",
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
        )
        TextButton(
            onClick = onClick,
            shape = MaterialTheme.shapes.small,
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )

        }
    }
}

@Composable
fun SignUpFields(modifier: Modifier = Modifier) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = "",
            label = { Text("Full Name") },
            onValueChange = { },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Person Icon"
                )
            },
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = "",
            label = { Text("Email") },
            onValueChange = { },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email Icon"
                )
            },
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = "",
            label = { Text("Password") },
            onValueChange = { },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Lock Icon"
                )
            },
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = "",
            label = { Text("Password Confirmation") },
            onValueChange = { },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Lock Icon"
                )
            },
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .height(64.dp), contentAlignment = Alignment.Center
        ) {
            ElevatedButton(
                modifier = Modifier.fillMaxSize(),
                onClick = { },
                shape = MaterialTheme.shapes.small,
            ) {
                Text(
                    text = "NEXT",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,

                    )

            }
        }
    }
}
