package com.clarxlabs.ellion.auth.data.remote.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CredentialsData(
    @SerialName("email")
    val email: String = "",

    @SerialName("password")
    val password: String = ""
)
