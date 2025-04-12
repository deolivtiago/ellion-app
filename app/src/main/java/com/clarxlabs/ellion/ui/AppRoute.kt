package com.clarxlabs.ellion.ui

import kotlinx.serialization.Serializable

@Serializable
sealed interface AppRoute {
    @Serializable
    data object AuthGraph : AppRoute

    @Serializable
    data object SignIn : AppRoute

    @Serializable
    data object SignUp : AppRoute

    @Serializable
    data class Verify(val email: String = "") : AppRoute

    @Serializable
    data class Confirm(val email: String = "") : AppRoute

    @Serializable
    data class Home(val accessToken: String = "", val refreshToken: String = "") : AppRoute

    @Serializable
    data object ListUsers : AppRoute

    @Serializable
    data class Profile(val userId: String = "") : AppRoute
}
