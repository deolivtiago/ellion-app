package com.clarxlabs.ellion.auth.data.remote.dtos

import com.clarxlabs.ellion.application.utilities.ResultError
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokensError(
    @SerialName("access_token")
    val accessToken: List<String> = emptyList(),

    @SerialName("refresh_token")
    val refreshToken: List<String> = emptyList(),
) : ResultError
