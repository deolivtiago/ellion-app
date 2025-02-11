package com.clarxlabs.ellion

import androidx.compose.runtime.Composable
import com.clarxlabs.ellion.auth.signin.SignInView
import com.clarxlabs.ellion.auth.signin.SignInViewModel
import com.clarxlabs.ellion.auth.signin.data.RemoteDataSource
import com.clarxlabs.ellion.core.HttpClientFactory
import com.clarxlabs.ellion.ui.theme.EllionTheme
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.compose.KoinApplication
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val networkModule = module {
    single<HttpClientEngine> { OkHttp.create() }
    single<HttpClient> { HttpClientFactory.create(get()) }
}

val remoteDataSourcesModule = module { singleOf(::RemoteDataSource) }

val viewModelsModule = module { viewModelOf(::SignInViewModel) }

@Composable
fun MainApplication() {
    KoinApplication(application = {
        modules(
            networkModule,
            remoteDataSourcesModule,
            viewModelsModule
        )
    }) { EllionTheme { SignInView() } }
}
