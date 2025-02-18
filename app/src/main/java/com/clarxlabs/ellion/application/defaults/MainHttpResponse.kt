package com.clarxlabs.ellion.application.defaults

import kotlinx.serialization.Serializable

@Serializable
sealed interface MainHttpResponse {
    @Serializable
    data class OkResponse<out T>(val data: T) : MainHttpResponse

    @Serializable
    data class CreatedResponse<T>(val data: T) : MainHttpResponse

    @Serializable
    data class UnprocessableEntity<T>(val errors: T) : MainHttpResponse

    @Serializable
    data class Unauthorized<T>(val errors: T?) : MainHttpResponse

    @Serializable
    data class Forbidden<T>(val errors: T?) : MainHttpResponse

    @Serializable
    data class InternalServerError<T>(val errors: T?) : MainHttpResponse
}
