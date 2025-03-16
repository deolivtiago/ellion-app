package com.clarxlabs.ellion.auth.presentation.home

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.clarxlabs.ellion.application.config.NavRoute
import com.clarxlabs.ellion.auth.domain.services.models.LoginInput
import com.clarxlabs.ellion.auth.presentation.AppModel
import kotlinx.serialization.Serializable

sealed interface HomeModel : AppModel {
    @Serializable
    data class State(
        val accessToken: String = "jwt.access.token",
        val refreshToken: String = "jwt.refresh.token",
        val input: LoginInput = LoginInput(),
        val error: LoginInput = LoginInput(),

        val email: String = "deoliv.tiago@gmail.com",
        val emailError: String = "",
        val password: String = "4m1Mad?",
        val passwordError: String = "",

        val isLoading: Boolean = false,
    ) : AppModel.State {
        constructor(handle: SavedStateHandle) : this(
            accessToken = handle.toRoute<NavRoute.Home>().accessToken,
            refreshToken = handle.toRoute<NavRoute.Home>().refreshToken,
        )
    }

    sealed interface Event : AppModel.Event {
        data class OnAccessTokenChanged(val accessToken: String) : Event
        data class OnRefreshTokenChanged(val refreshToken: String) : Event
        data class OnSignOutClicked(val navigateTo: (NavRoute) -> Unit) : Event
        data class OnEmailChanged(val text: String) : Event
        data class OnPasswordChanged(val text: String) : Event
        data class OnSubmitClicked(val navigateTo: (NavRoute) -> Unit) : Event
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
