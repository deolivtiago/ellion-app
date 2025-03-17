package com.clarxlabs.ellion.application.utilities

import kotlinx.serialization.Serializable

sealed interface Either<out S, out F> {
    @Serializable
    data class Success<out S, out F>(val output: S) : Either<S, F>

    @Serializable
    data class Failure<out S, out F>(val output: F) : Either<S, F>
}
