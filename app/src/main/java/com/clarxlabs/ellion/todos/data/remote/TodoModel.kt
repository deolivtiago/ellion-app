package com.clarxlabs.ellion.todos.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TodoModel(
    @SerialName("id")
    val id: Int,

    @SerialName("title")
    val title: String,

    @SerialName("completed")
    val completed: Boolean,
)
