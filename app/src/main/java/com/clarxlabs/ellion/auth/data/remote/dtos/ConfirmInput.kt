package com.clarxlabs.ellion.auth.data.remote.dtos

import kotlinx.serialization.Serializable

@Serializable
data class ConfirmInput(val email: String, val code: String)
