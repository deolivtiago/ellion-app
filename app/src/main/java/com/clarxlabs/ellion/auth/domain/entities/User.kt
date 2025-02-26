package com.clarxlabs.ellion.auth.domain.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String = "",

    @SerialName("first_name")
    val firstName: String,

    val email: String,

    @SerialName("last_name")
    val lastName: String = ""
)
