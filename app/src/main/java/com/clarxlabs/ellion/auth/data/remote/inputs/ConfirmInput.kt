package com.clarxlabs.ellion.auth.data.remote.inputs

import kotlinx.serialization.Serializable

@Serializable
data class ConfirmInput(val email: String, val code: String)
