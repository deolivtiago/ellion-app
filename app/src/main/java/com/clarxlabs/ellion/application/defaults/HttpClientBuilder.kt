package com.clarxlabs.ellion.application.defaults

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.headers
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class HttpClientBuilder {
    private var engine: HttpClientEngineFactory<HttpClientEngineConfig> = OkHttp
    private var protocol: URLProtocol = URLProtocol.HTTPS
    private var host: String = "ellion.gigalixirapp.com"

    fun engine(engine: HttpClientEngineFactory<HttpClientEngineConfig>) =
        apply { this.engine = engine }

    fun protocol(protocol: URLProtocol) =
        apply { this.protocol = protocol }

    fun host(host: String) =
        apply { this.host = host }

    fun build(): HttpClient =
        HttpClient(this@HttpClientBuilder.engine) {
            expectSuccess = true

            defaultRequest {
                url {
                    protocol = this@HttpClientBuilder.protocol
                    host = this@HttpClientBuilder.host
                }
                headers { append("Accept", "application/json") }
            }

            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                })
            }

            install(HttpTimeout) {
                socketTimeoutMillis = 20_000L
                requestTimeoutMillis = 20_000L
            }

            install(HttpRequestRetry) {
                retryOnServerErrors(maxRetries = 5)
                exponentialDelay()
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
                level = LogLevel.ALL
            }

        }
}
