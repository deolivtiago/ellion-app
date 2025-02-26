package com.clarxlabs.ellion.auth.data.remote.dtos

import com.clarxlabs.ellion.application.utilities.ResultError
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDataError(
    @SerialName("first_name")
    val firstName: List<String> = emptyList(),

    @SerialName("last_name")
    val lastName: List<String> = emptyList(),

    val email: List<String> = emptyList(),

    val password: List<String> = emptyList(),

    @SerialName("avatar_url")
    val avatarUrl: List<String> = emptyList(),

    val role: List<String> = emptyList(),
) : ResultError
