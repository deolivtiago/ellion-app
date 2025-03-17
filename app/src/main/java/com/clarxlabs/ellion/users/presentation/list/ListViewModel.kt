package com.clarxlabs.ellion.users.presentation.list

import androidx.lifecycle.viewModelScope
import com.clarxlabs.ellion.application.utilities.Either
import com.clarxlabs.ellion.auth.data.remote.AuthenticationDataSource
import com.clarxlabs.ellion.auth.data.remote.AuthenticationDataSource.ListUsers
import com.clarxlabs.ellion.auth.domain.entities.User
import com.clarxlabs.ellion.auth.presentation.AppViewModel
import kotlinx.coroutines.launch

class ListViewModel(
    private val authenticationDataSource: AuthenticationDataSource,
) : AppViewModel<ListModel.State, ListModel.Event>(ListModel.State()) {

    init {
        fetchUsers(::mapResult)
    }

    override fun onEvent(event: ListModel.Event) {
        when (event) {
            ListModel.Event.OnCardClicked -> {}
        }
    }

    fun fetchUsers(onResponse: (Either<ListUsers.Output, ListUsers.Error>) -> Unit = {}) {
        viewModelScope.launch { onResponse(authenticationDataSource.listUsers()) }
    }

    fun mapResult(result: Either<ListUsers.Output, ListUsers.Error>) {
        when (result) {
            is Either.Success -> setState {
                it.copy(
                    isLoading = false,
                    users = result.output
                        .users.map { User(it.id, it.firstName, it.email, it.lastName) }
                )
            }

            is Either.Failure -> setState { it.copy(isLoading = false) }
        }
    }
}
