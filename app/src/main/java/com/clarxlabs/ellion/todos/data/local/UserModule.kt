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
object UserModule {

    @Singleton
    @Provides
    fun provideUserDatabase(@ApplicationContext context: Context): UserDatabase {
        val databaseName =
            context
                .applicationContext
                .applicationInfo
                .loadLabel(context.applicationContext.packageManager)
                .toString()

        return Room
            .databaseBuilder(context.applicationContext, UserDatabase::class.java, databaseName)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideUserDao(database: UserDatabase): UserDao {
        return database.userDao
    }
}
