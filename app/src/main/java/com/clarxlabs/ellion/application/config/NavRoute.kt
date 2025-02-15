package com.clarxlabs.ellion.application.config

import kotlinx.serialization.Serializable

sealed interface NavRoute {
    @Serializable
    data object AuthGraph : NavRoute

    @Serializable
    data object SignIn : NavRoute

    @Serializable
    data object SignUp : NavRoute
}
