package com.clarxlabs.ellion.ui.users.list

import com.clarxlabs.ellion.core.entities.User
import com.clarxlabs.ellion.ui.AppModel

sealed interface ListModel {
    data class State(
        val users: List<User> = emptyList(),
        val isLoading: Boolean = true,
    ) : AppModel.State

    sealed interface Event : AppModel.Event {
        data object OnCardClicked : Event
    }
}
