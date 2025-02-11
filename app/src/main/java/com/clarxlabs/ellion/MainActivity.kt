package com.clarxlabs.ellion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.clarxlabs.ellion.auth.signin.SignInModelState
import com.clarxlabs.ellion.auth.signin.SignInViewContent
import com.clarxlabs.ellion.ui.theme.EllionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MainApplication() }
    }
}

@Preview(device = "spec:parent=pixel_9_pro_xl", showSystemUi = true)
@Composable
fun MainAppPreview() {
    EllionTheme {
        SignInViewContent(
            state = SignInModelState(email = "alice@wonderland.co"),
            onEvent = {}
        )
    }
}
