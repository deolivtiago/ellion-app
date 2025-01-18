package com.clarxlabs.ellion.todos.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Query("select * from users")
    fun listUsers(): List<UserEntity>

    @Query("select * from users where id = :id")
    suspend fun getUser(id: Int): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createUser(user: UserEntity): Long

    @Delete
    suspend fun deleteUser(user: UserEntity): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createUsers(users: List<UserEntity>): List<Long>
}
