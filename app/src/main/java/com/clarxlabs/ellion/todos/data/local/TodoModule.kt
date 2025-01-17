package com.clarxlabs.ellion.todos.data.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TodoModule {

    @Singleton
    @Provides
    fun provideTodoDatabase(@ApplicationContext context: Context): TodoDatabase {
        val databaseName =
            context
                .applicationContext
                .applicationInfo
                .loadLabel(context.applicationContext.packageManager)
                .toString()

        return Room
            .databaseBuilder(context.applicationContext, TodoDatabase::class.java, databaseName)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideTodoDao(database: TodoDatabase): TodoDao {
        return database.todoDao
    }
}
