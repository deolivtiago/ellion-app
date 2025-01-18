package com.clarxlabs.ellion.todos.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, exportSchema = false, entities = [UserEntity::class])
abstract class UserDatabase : RoomDatabase() {
    abstract val userDao: UserDao

    companion object {
        const val DATABASE_NAME = "ellion"
    }
}
