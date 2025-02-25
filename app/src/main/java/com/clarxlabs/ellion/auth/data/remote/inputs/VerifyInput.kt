package com.clarxlabs.ellion.auth.data.remote.inputs

import kotlinx.serialization.Serializable

@Serializable
data class VerifyInput(
    val email: String = ""
)
