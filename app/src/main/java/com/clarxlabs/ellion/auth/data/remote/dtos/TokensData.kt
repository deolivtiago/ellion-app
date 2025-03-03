package com.clarxlabs.ellion.auth.data.remote.dtos

import com.clarxlabs.ellion.application.utilities.ResultData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokensData(
    @SerialName("access_token")
    val accessToken: String = "",

    @SerialName("refresh_token")
    val refreshToken: String = ""
) : ResultData
