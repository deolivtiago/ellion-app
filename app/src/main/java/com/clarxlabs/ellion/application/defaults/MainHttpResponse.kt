package com.clarxlabs.ellion.application.defaults

sealed interface MainHttpResponse {
    data class OkResponse<out T>(val data: T) : MainHttpResponse
    data class CreatedResponse<T>(val data: T) : MainHttpResponse
    data class UnprocessableEntity<T>(val errors: T) : MainHttpResponse
    data class Unauthorized<T>(val errors: T?) : MainHttpResponse
    data class Forbidden<T>(val errors: T?) : MainHttpResponse
    data class InternalServerError<T>(val errors: T?) : MainHttpResponse
}
