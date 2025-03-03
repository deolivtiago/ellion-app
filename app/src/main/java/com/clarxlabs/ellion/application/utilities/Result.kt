package com.clarxlabs.ellion.application.utilities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed interface Result<out D : ResultData, out E : ResultError> {
    @Serializable
    data class Data<out D : ResultData, out E : ResultError>(
        val data: D
    ) : Result<D, E>

    @Serializable
    data class Error<out D : ResultData, out E : ResultError>(
        @SerialName("errors") val error: E
    ) : Result<D, E>
}
