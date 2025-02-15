package com.clarxlabs.ellion.application.defaults

sealed interface MainHttpRequest {
    data class GetRequest(
        val url: String,
        val headers: Map<String, String> = emptyMap(),
        val queries: Map<String, String> = emptyMap(),
    ) : MainHttpRequest

    data class PostRequest(
        val url: String,
        val body: Map<String, Any>,
        val headers: Map<String, String> = emptyMap(),
        val queries: Map<String, String> = emptyMap(),
    ) : MainHttpRequest

    data class PutRequest(
        val url: String,
        val body: Map<String, Any>,
        val headers: Map<String, String> = emptyMap(),
        val queries: Map<String, String> = emptyMap(),
    ) : MainHttpRequest

    data class DeleteRequest(
        val url: String,
        val headers: Map<String, String> = emptyMap(),
        val queries: Map<String, String> = emptyMap(),
    ) : MainHttpRequest
}
