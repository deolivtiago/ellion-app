package com.clarxlabs.ellion.auth.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.clarxlabs.ellion.application.config.NavRoute
import com.clarxlabs.ellion.application.theme.EllionTheme
import com.clarxlabs.ellion.auth.presentation.components.ActionButton
import kotlinx.coroutines.launch


@Composable
fun HomeView(viewModel: HomeViewModel, onNavigate: (NavRoute) -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent

    HomeViewContent(state, onEvent, onNavigate)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeViewContent(
    state: HomeModel.State,
    onEvent: (HomeModel.Event) -> Unit,
    onNavigate: (NavRoute) -> Unit,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        ModalDrawerSheet(
            drawerState = drawerState,
            drawerContainerColor = MaterialTheme.colorScheme.primary,
            drawerContentColor = MaterialTheme.colorScheme.onPrimary,
            drawerShape = MaterialTheme.shapes.extraSmall,
        ) {
            NavigationDrawerItem(
                label = {
                    Text(
                        text = "Olá, Alice",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.onPrimary,
                        ),
                    )
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                },
                selected = false,
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                colors = NavigationDrawerItemDefaults.colors(),
                onClick = {
                    scope.launch { drawerState.close() }
                },
            )

            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.onPrimary)
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 8.dp)
                    .weight(1F),
            ) {
                (1..4).toList().forEach {
                    NavigationDrawerItem(
                        label = { Text(text = "Item $it") },
                        icon = {
                            Icon(imageVector = Icons.Default.Person, contentDescription = "")
                        },
                        selected = it == 2,
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                        colors = NavigationDrawerItemDefaults.colors(),
                        onClick = {
                            scope.launch { drawerState.close() }
                        },
                        shape = MaterialTheme.shapes.large,
                    )
                }
            }

            Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.onPrimary)) {
                ActionButton(
                    actionTitle = "Desconectar",
                    onClicked = {
                        scope.launch {
                            drawerState.close()
                            onEvent(HomeModel.Event.OnSignOutClicked(onNavigate))
                        }
                    },
                    modifier = Modifier.padding(8.dp),
                )
//                NavigationDrawerItem(
//                    label = {
//                        Text(
//                            text = "Desconectar",
//                            style = MaterialTheme.typography.titleMedium.copy(
//                                fontWeight = FontWeight.Bold,
//                                color = MaterialTheme.colorScheme.onPrimary,
//                            ),
//                        )
//                    },
//                    icon = {
//                        Icon(
//                            imageVector = Icons.Default.Output,
//                            contentDescription = "",
//                            tint = MaterialTheme.colorScheme.onPrimary,
//                        )
//                    },
//                    selected = false,
//                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
////                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
//                    colors = NavigationDrawerItemDefaults.colors(),
//                    onClick = {
//                        scope.launch {
//                            drawerState.close()
//                            onEvent(HomeModel.Event.OnSignOutClicked(onNavigate))
//                        }
//                    },
//                    shape = MaterialTheme.shapes.large,
//                )
            }
        }
    }) {
        Scaffold(
            modifier = Modifier
                .navigationBarsPadding()
                .fillMaxSize(),

            topBar = {
                TopAppBar(
                    title = { Text("Ellion") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu icon",
                                tint = MaterialTheme.colorScheme.onPrimary,
                            )
                        }
                    })
            }) { _ ->
            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
//                    .padding(it)
                    .padding(8.dp)
                    .imePadding()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    Text(text = state.accessToken)
                    Text(text = state.refreshToken)
                }


            }
        }
    }
}

@Preview(showSystemUi = true, device = "spec:parent=pixel_3a")
@Composable
fun HomePreviewPhone() {
    EllionTheme {
        HomeViewContent(
            state = HomeModel.State(),
            onEvent = {},
            onNavigate = {},
        )
    }
}

@Preview(showSystemUi = true, device = "spec:parent=Galaxy Nexus,navigation=buttons")
@Composable
fun HomePreviewPhoneSmall() {
    EllionTheme {
        HomeViewContent(
            state = HomeModel.State(),
            onEvent = {},
            onNavigate = {},
        )
    }
}

@Preview(device = "spec:parent=Nexus 7 2013,navigation=buttons", showSystemUi = true)
@Composable
fun HomePreviewTabletPortrait() {
    EllionTheme {
        HomeViewContent(
            state = HomeModel.State(),
            onEvent = {},
            onNavigate = {},
        )
    }
}

@Preview(device = "spec:parent=Nexus 10,navigation=buttons", showSystemUi = true)
@Composable
fun HomePreviewTabletLandscape() {
    EllionTheme {
        HomeViewContent(
            state = HomeModel.State(),
            onEvent = {},
            onNavigate = {},
        )
    }
}
