package com.clarxlabs.ellion.todos.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface TodoApi {

    @GET("todos")
    suspend fun listTodos(): List<TodoModel>

    @GET("todos/{id}")
    suspend fun getTodo(@Path("id") id: Int): TodoModel
}
