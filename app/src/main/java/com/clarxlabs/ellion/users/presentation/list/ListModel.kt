package com.clarxlabs.ellion.users.presentation.list

import com.clarxlabs.ellion.auth.domain.entities.User
import com.clarxlabs.ellion.auth.presentation.AppModel

sealed interface ListModel {
    data class State(
        val users: List<User> = emptyList(),
        val isLoading: Boolean = true,
    ) : AppModel.State

    sealed interface Event : AppModel.Event {
        data object OnCardClicked : Event
    }
}
