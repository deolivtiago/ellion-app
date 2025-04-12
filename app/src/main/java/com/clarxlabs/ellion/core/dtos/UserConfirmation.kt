package com.clarxlabs.ellion.core.dtos

import kotlinx.serialization.Serializable

@Serializable
data class UserConfirmation(
    val email: String = "",
    val code: String = "",
) {
    @Serializable
    data class Error(
        val email: List<String> = emptyList(),
        val code: List<String> = emptyList(),
    )
}
