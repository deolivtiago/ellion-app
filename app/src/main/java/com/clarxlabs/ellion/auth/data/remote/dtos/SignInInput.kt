package com.clarxlabs.ellion.auth.data.remote.dtos

import kotlinx.serialization.Serializable

@Serializable
data class SignInInput(val email: String, val password: String)
