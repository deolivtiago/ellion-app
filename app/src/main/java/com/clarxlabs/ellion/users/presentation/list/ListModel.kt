package com.clarxlabs.ellion.users.presentation.list

import com.clarxlabs.ellion.auth.domain.entities.User

sealed interface ListModel {
    data class State(
        val users: List<User> = emptyList(),
        val isLoading: Boolean = true,
    ) : ListModel

    sealed interface Event {
        data object OnCardClicked : Event
    }
}
