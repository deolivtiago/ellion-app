package com.clarxlabs.ellion.auth.data.remote.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokensData(
    @SerialName("access_token")
    val accessToken: String = "",

    @SerialName("refresh_token")
    val refreshToken: String = ""
)
