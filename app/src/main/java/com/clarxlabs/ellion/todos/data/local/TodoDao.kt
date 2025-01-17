package com.clarxlabs.ellion.todos.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TodoDao {
    @Query("select * from todos")
    fun listTodos(): List<TodoEntity>

    @Query("select * from todos where id = :id")
    suspend fun getTodo(id: Int): TodoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createTodo(todo: TodoEntity): Long

    @Delete
    suspend fun deleteTodo(todo: TodoEntity): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createTodos(todos: List<TodoEntity>): List<Long>
}
