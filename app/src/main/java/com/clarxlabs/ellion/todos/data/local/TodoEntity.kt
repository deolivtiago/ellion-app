package com.clarxlabs.ellion.todos.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    val title: String,
    val completed: Boolean,
)
