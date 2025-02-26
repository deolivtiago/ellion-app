package com.clarxlabs.ellion.users.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clarxlabs.ellion.application.defaults.MainHttpResponse
import com.clarxlabs.ellion.auth.data.remote.AuthDataSource
import com.clarxlabs.ellion.auth.domain.entities.User
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListViewModel(private val authDataSource: AuthDataSource) : ViewModel() {
    private val initialState = ListModel.State()
    private val _state = MutableStateFlow(initialState)
    private val setState = _state::update
    val state = _state.asStateFlow()

    init {
        fetchUsers { setState { it.copy(isLoading = false) } }
    }

    fun onEvent(event: ListModel.Event) {
        when (event) {
            ListModel.Event.OnCardClicked -> {}
        }
    }

    fun fetchUsers(onResponse: (HttpResponse) -> Unit = {}) {
        viewModelScope.launch {
            val response = authDataSource.listUsers()

            if (response.status.value == 200) {
                val users = response
                    .body<MainHttpResponse.OkResponse<List<User>>>()
                    .data
                setState { it.copy(users) }
            }

            onResponse(response)
        }
    }
}
