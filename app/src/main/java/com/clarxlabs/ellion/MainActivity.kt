package com.clarxlabs.ellion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.clarxlabs.ellion.auth.ui.SignInView
import com.clarxlabs.ellion.ui.theme.EllionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { EllionTheme { SignInView() } }
    }
}

@Composable fun HomeView(modifier: Modifier = Modifier) {}

@Preview
@Composable
fun Preview() {
    EllionTheme { SignInView() }
}
