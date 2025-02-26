package com.clarxlabs.ellion.auth.data.remote.dtos

import com.clarxlabs.ellion.application.utilities.ResultError
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CredentialsError(
    @SerialName("email")
    val email: List<String> = emptyList(),

    @SerialName("password")
    val password: List<String> = emptyList(),
) : ResultError
