package com.clarxlabs.ellion.auth.data.remote.inputs

import kotlinx.serialization.Serializable

@Serializable
data class SignInInput(
    val email: String = "",
    val password: String = ""
)
