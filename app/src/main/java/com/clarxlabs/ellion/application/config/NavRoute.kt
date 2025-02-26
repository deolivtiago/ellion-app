package com.clarxlabs.ellion.application.config

import kotlinx.serialization.Serializable

@Serializable
sealed interface NavRoute {
    @Serializable
    data object AuthGraph : NavRoute

    @Serializable
    data object SignIn : NavRoute

    @Serializable
    data object SignUp : NavRoute

    @Serializable
    data class Verify(val email: String = "") : NavRoute

    @Serializable
    data class Confirm(val email: String = "") : NavRoute

    @Serializable
    data class Home(val accessToken: String = "", val refreshToken: String = "") : NavRoute

    @Serializable
    data object ListUsers : NavRoute
}
