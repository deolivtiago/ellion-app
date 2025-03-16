package com.clarxlabs.ellion.auth.domain.services.models

import com.clarxlabs.ellion.application.utilities.Either
import kotlinx.serialization.Serializable


interface Registration {
    @Serializable
    abstract class Properties : Registration {
        abstract val email: String
        abstract val password: String
    }

    @Serializable
    class Input(
        override val email: String,
        override val password: String,
    ) : Properties()

    @Serializable
    class Output(
        override val email: String = "deoliv.tiago@gmail.com",
        override val password: String = "4m1Mad?",
    ) : Properties()

    @Serializable
    class Error(
        override val email: String = "",
        override val password: String = ""
    ) : Properties()

    object Factory {
        fun create(input: Input): Either<Output, Error> {
            val errors = listOf(
                errorMessage(input.email) { it.isNotBlank() },
                errorMessage(input.password) { it.isNotBlank() }
            )

            if (errors.any { it.isNotEmpty() })
                return Either.Failure(Error(errors.first(), errors.last()))

            return Either.Success(Output())
        }

        private fun errorMessage(text: String, condition: (String) -> Boolean) =
            condition(text).let {
                if (it) "" else "invalid field"
            }
    }
}


