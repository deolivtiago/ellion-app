package com.clarxlabs.ellion.application.utilities

import kotlinx.serialization.Serializable

sealed interface Result<out D, out E : ResultError> {
    @Serializable
    data class Data<out D, out E : ResultError>(val data: D) : Result<D, E>

    @Serializable
    data class Error<out D, out E : ResultError>(val errors: E) : Result<D, E>
}
