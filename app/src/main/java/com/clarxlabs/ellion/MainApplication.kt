package com.clarxlabs.ellion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.clarxlabs.ellion.core.datasources.AuthenticationDataSource
import com.clarxlabs.ellion.core.datasources.AuthenticationDataSourceImpl
import com.clarxlabs.ellion.core.datasources.factories.HttpClientFactory
import com.clarxlabs.ellion.core.repositories.AuthenticationRepository
import com.clarxlabs.ellion.core.repositories.AuthenticationRepositoryImpl
import com.clarxlabs.ellion.core.services.ValidationService
import com.clarxlabs.ellion.core.services.ValidationServiceImpl
import com.clarxlabs.ellion.ui.AppRoute
import com.clarxlabs.ellion.ui.auth.confirm.ConfirmView
import com.clarxlabs.ellion.ui.auth.confirm.ConfirmViewModel
import com.clarxlabs.ellion.ui.auth.profile.ProfileView
import com.clarxlabs.ellion.ui.auth.profile.ProfileViewModel
import com.clarxlabs.ellion.ui.auth.signin.SignInView
import com.clarxlabs.ellion.ui.auth.signin.SignInViewModel
import com.clarxlabs.ellion.ui.auth.signup.SignUpView
import com.clarxlabs.ellion.ui.auth.signup.SignUpViewModel
import com.clarxlabs.ellion.ui.auth.verify.VerifyView
import com.clarxlabs.ellion.ui.auth.verify.VerifyViewModel
import com.clarxlabs.ellion.ui.home.HomeView
import com.clarxlabs.ellion.ui.home.HomeViewModel
import com.clarxlabs.ellion.ui.theme.EllionTheme
import com.clarxlabs.ellion.ui.users.list.ListView
import com.clarxlabs.ellion.ui.users.list.ListViewModel
import io.ktor.client.HttpClient
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module


@Composable
fun MainApplication() {
    val appModule = module {
        singleOf(HttpClientFactory::create).bind<HttpClient>()
        singleOf(::AuthenticationDataSourceImpl).bind<AuthenticationDataSource>()
        singleOf(::AuthenticationRepositoryImpl).bind<AuthenticationRepository>()
        singleOf(::ValidationServiceImpl).bind<ValidationService>()

        viewModelOf(::SignInViewModel)
        viewModelOf(::SignUpViewModel)
        viewModelOf(::VerifyViewModel)
        viewModelOf(::ConfirmViewModel)
        viewModelOf(::HomeViewModel)
        viewModelOf(::ProfileViewModel)
        viewModelOf(::ListViewModel)
    }

    KoinApplication({ modules(appModule) }) {
        EllionTheme {
            val navController = rememberNavController()
            NavHost(navController, AppRoute.AuthGraph) {
                navigation<AppRoute.AuthGraph>(startDestination = AppRoute.SignIn) {
                    composable<AppRoute.SignIn> {
                        SignInView(
                            viewModel = koinViewModel(),
                            navigateTo = navController::navigate,
                        )
                    }
                    composable<AppRoute.SignUp> {
                        SignUpView(
                            viewModel = koinViewModel(),
                            navigateTo = navController::navigate,
                        )
                    }
                    composable<AppRoute.Verify> {
                        VerifyView(
                            viewModel = koinViewModel(),
                            onNavigate = navController::navigate,
                        )
                    }
                    composable<AppRoute.Confirm> {
                        ConfirmView(
                            viewModel = koinViewModel(),
                            onNavigate = navController::navigate,
                        )
                    }
                    composable<AppRoute.Home> {
                        HomeView(
                            viewModel = koinViewModel(),
                            navigateTo = navController::navigate,
                        )
                    }
                    composable<AppRoute.ListUsers> {
                        ListView(
                            viewModel = koinViewModel(),
                            onNavigate = navController::navigate,
                        )
                    }
                    composable<AppRoute.Profile> {
                        ProfileView(
                            viewModel = koinViewModel(),
                            navigateTo = navController::navigate,
                        )
                    }
                }
            }
        }
    }
}
