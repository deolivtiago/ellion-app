package com.clarxlabs.ellion.auth.data.remote.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpInput(
    @SerialName("first_name")
    val firstName: String,
    @SerialName("last_name")
    val lastName: String = "",
    val email: String,
    val password: String,
    @SerialName("avatar_url")
    val avatarUrl: String = "",
)
