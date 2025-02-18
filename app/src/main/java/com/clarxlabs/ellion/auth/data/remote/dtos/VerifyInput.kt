package com.clarxlabs.ellion.auth.data.remote.dtos

import kotlinx.serialization.Serializable

@Serializable
data class VerifyInput(
    val email: String = ""
)
