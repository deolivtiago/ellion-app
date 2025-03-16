package com.clarxlabs.ellion.application.utilities

import kotlinx.serialization.Serializable

sealed interface Either<out S, out E> {
    @Serializable
    data class Success<out S, out E>(val output: S) : Either<S, E>

    @Serializable
    data class Failure<out S, out E>(val output: E) : Either<S, E>
}
