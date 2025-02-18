package com.clarxlabs.ellion.auth.presentation.home

import com.clarxlabs.ellion.application.config.NavRoute
import kotlinx.serialization.Serializable

sealed interface HomeModel {
    @Serializable
    data class State(
        val accessToken: String = "jwt.access.token",
        val refreshToken: String = "jwt.refresh.token",
        val isLoading: Boolean = false,
    ) : HomeModel

    sealed interface Event {
        data class OnAccessTokenChanged(val accessToken: String) : Event
        data class OnRefreshTokenChanged(val refreshToken: String) : Event
        data class OnSignOutClicked(val navigateTo: (NavRoute) -> Unit) : Event
    }
}

//internal val HomeModelStateType = object : NavType<HomeModelState>(isNullableAllowed = false) {
//    override fun get(bundle: Bundle, key: String): HomeModelState? =
//        bundle.getString(key)?.let { parseValue(it) }
//
//    override fun put(bundle: Bundle, key: String, value: HomeModelState) {
//        bundle.putString(key, serializeAsValue(value))
//    }
//
//    override fun parseValue(value: String): HomeModelState = Json.decodeFromString(value)
//
//    override fun serializeAsValue(value: HomeModelState): String = Json.encodeToString(value)
//}

//
//abstract class DefaultType<T> : NavType<T>(false) {
//    override fun get(bundle: Bundle, key: String): T? =
//        bundle.getString(key)?.let { parseValue(it) }
//
//    override fun put(bundle: Bundle, key: String, value: T) {
//        bundle.putString(key, serializeAsValue(value))
//    }
//
//    override fun parseValue(value: String): T = Json.decodeFromString(value)
//
//    override fun serializeAsValue(value: T): String = Json.encodeToString(value)
//}
