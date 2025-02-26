package com.clarxlabs.ellion.users.presentation.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.clarxlabs.ellion.application.config.NavRoute
import com.clarxlabs.ellion.application.theme.EllionTheme

@Composable
fun ListView(viewModel: ListViewModel, onNavigate: (NavRoute) -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent

    ListViewContent(state, onEvent, onNavigate)
}

@Composable
fun ListViewContent(
    state: ListModel.State,
    onEvent: (ListModel.Event) -> Unit,
    onNavigate: (NavRoute) -> Unit,
) {
    Surface(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize(),
    ) {
        if (state.isLoading) Box(contentAlignment = Alignment.Center) { CircularProgressIndicator() }
        else LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            items(state.users, key = { it.id }) {
                Card {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Nome:")
                            Text(it.firstName)
                            Text(it.lastName)
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Email:")
                            Text(it.email)
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                it.id,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.outline,
                                )

                            )
                        }
                    }
                }
            }
        }
    }

}

@Preview(showSystemUi = true, device = "spec:parent=pixel_3a")
@Composable
fun PreviewPhone() {
    EllionTheme {
        ListViewContent(
            state = ListModel.State(),
            onEvent = {},
            onNavigate = {},
        )
    }
}

@Preview(showSystemUi = true, device = "spec:width=720px,height=1280px,dpi=320,navigation=buttons")
@Composable
fun PreviewPhoneSmall() {
    EllionTheme {
        ListViewContent(
            state = ListModel.State(),
            onEvent = {},
            onNavigate = {},
        )
    }
}

@Preview(device = "spec:parent=Nexus 7 2013,navigation=buttons", showSystemUi = true)
@Composable
fun PreviewTabletPortrait() {
    EllionTheme {
        ListViewContent(
            state = ListModel.State(),
            onEvent = {},
            onNavigate = {},
        )
    }
}

@Preview(device = "spec:parent=Nexus 10,navigation=buttons", showSystemUi = true)
@Composable
fun PreviewTabletLandscape() {
    EllionTheme {
        ListViewContent(
            state = ListModel.State(),
            onEvent = {},
            onNavigate = {},
        )
    }
}
