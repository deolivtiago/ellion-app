package com.clarxlabs.ellion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.clarxlabs.ellion.application.config.NavRoute
import com.clarxlabs.ellion.application.factories.HttpClientFactory
import com.clarxlabs.ellion.application.theme.EllionTheme
import com.clarxlabs.ellion.auth.data.remote.AuthDataSource
import com.clarxlabs.ellion.auth.data.remote.MainAuthDataSource
import com.clarxlabs.ellion.auth.presentation.confirm.ConfirmView
import com.clarxlabs.ellion.auth.presentation.confirm.ConfirmViewModel
import com.clarxlabs.ellion.auth.presentation.home.HomeView
import com.clarxlabs.ellion.auth.presentation.home.HomeViewModel
import com.clarxlabs.ellion.auth.presentation.signin.SignInView
import com.clarxlabs.ellion.auth.presentation.signin.SignInViewModel
import com.clarxlabs.ellion.auth.presentation.signup.SignUpView
import com.clarxlabs.ellion.auth.presentation.signup.SignUpViewModel
import com.clarxlabs.ellion.auth.presentation.verify.VerifyView
import com.clarxlabs.ellion.auth.presentation.verify.VerifyViewModel
import com.clarxlabs.ellion.users.presentation.list.ListView
import com.clarxlabs.ellion.users.presentation.list.ListViewModel
import io.ktor.client.HttpClient
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    singleOf(HttpClientFactory::create).bind<HttpClient>()
    singleOf(::MainAuthDataSource).bind<AuthDataSource>()

    viewModelOf(::SignInViewModel)
    viewModelOf(::SignUpViewModel)
    viewModelOf(::VerifyViewModel)
    viewModelOf(::ConfirmViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::ListViewModel)
}

@Composable
fun MainApplication() {
    KoinApplication({ modules(appModule) }) {
        EllionTheme {
            val navController = rememberNavController()
            NavHost(navController, NavRoute.AuthGraph) {
                navigation<NavRoute.AuthGraph>(startDestination = NavRoute.SignIn) {
                    composable<NavRoute.SignIn> {
                        SignInView(
                            viewModel = koinViewModel(),
                            onNavigate = navController::navigate,
                        )
                    }
                    composable<NavRoute.SignUp> {
                        SignUpView(
                            viewModel = koinViewModel(),
                            onNavigate = navController::navigate,
                        )
                    }
                    composable<NavRoute.Verify> {
                        VerifyView(
                            viewModel = koinViewModel(),
                            onNavigate = navController::navigate,
                        )
                    }
                    composable<NavRoute.Confirm> {
                        ConfirmView(
                            viewModel = koinViewModel(),
                            onNavigate = navController::navigate,
                        )
                    }
                    composable<NavRoute.Home> {
                        HomeView(
                            viewModel = koinViewModel(),
                            onNavigate = navController::navigate,
                        )
                    }
                    composable<NavRoute.ListUsers> {
                        ListView(
                            viewModel = koinViewModel(),
                            onNavigate = navController::navigate,
                        )
                    }
                }
            }
        }
    }
}

