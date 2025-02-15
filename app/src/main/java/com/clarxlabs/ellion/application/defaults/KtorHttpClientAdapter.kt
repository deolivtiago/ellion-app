package com.clarxlabs.ellion.application.defaults

import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import io.ktor.client.HttpClient as KtorHttpClient

class KtorHttpClientAdapter {
    private val client = KtorHttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(json = Json {
                ignoreUnknownKeys = true
                prettyPrint = true
            })
        }
        install(HttpTimeout) {
            socketTimeoutMillis = 20_000L
            requestTimeoutMillis = 20_000L
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

    suspend fun doRequest(request: MainHttpRequest): MainHttpResponse {
        when (request) {
//            is MainHttpRequest.GetRequest -> doGet<T>(request)
//            is MainHttpRequest.DeleteRequest -> doDelete<T>(request)
//            is MainHttpRequest.PostRequest -> doPost<T>(request)
//            is MainHttpRequest.PutRequest -> doPut<T>(request)
            else -> throw RuntimeException()
        }

    }

    private suspend inline fun <reified Tb> doGet(request: MainHttpRequest.GetRequest): MainHttpResponse {
        val response = client.get(request.url) {
            headers { request.headers.forEach { append(it.key, it.value) } }
            url { request.queries.forEach { parameters.append(it.key, it.value) } }
        }

        if (response.status.value in 200..201)
            return MainHttpResponse.OkResponse<Tb>(response.body<Tb>() as Tb)
        else
            throw RuntimeException()

    }

    private suspend fun <T> doPost(request: MainHttpRequest.PostRequest): MainHttpResponse {
        client.post(request.url) {
            headers { request.headers.forEach { append(it.key, it.value) } }
            url { request.queries.forEach { parameters.append(it.key, it.value) } }
            setBody(request.body)
        }
        throw RuntimeException()

        client.request(request.url) {
            method = HttpMethod.Get
        }
    }

    private suspend fun <T> doPut(request: MainHttpRequest.PutRequest): MainHttpResponse {
        client.put(request.url) {
            headers { request.headers.forEach { append(it.key, it.value) } }
            url { request.queries.forEach { parameters.append(it.key, it.value) } }
            setBody(request.body)
        }
        throw RuntimeException()
    }

    private suspend fun <T> doDelete(request: MainHttpRequest.DeleteRequest): MainHttpResponse {
        client.delete(request.url) {
            headers { request.headers.forEach { append(it.key, it.value) } }
            url { request.queries.forEach { parameters.append(it.key, it.value) } }
        }
        throw RuntimeException()
    }
}
