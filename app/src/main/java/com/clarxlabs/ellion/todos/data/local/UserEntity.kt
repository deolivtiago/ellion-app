package com.clarxlabs.ellion.todos.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    val firstName: String,
    val email: String,
    val password: String,
)
