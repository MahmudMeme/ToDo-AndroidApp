package com.example.todolist.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.todolist.data.LocalToDoDataSource
import com.example.todolist.data.RoomDataSource
import com.example.todolist.data.ToDoDao
import com.example.todolist.data.TodoDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SingletonModule {
    @Binds
    abstract fun bindLocalDataSource(impl: RoomDataSource): LocalToDoDataSource

    companion object {
        private const val DATABASE_NAME = "todos.db"

        @Provides
        fun provideContext(app: Application): Context {
            return app
        }

        @Provides
        @Singleton
        fun provideTodoDatabase(context: Context): TodoDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                TodoDatabase::class.java,
                DATABASE_NAME
            ).build()
        }

        @Provides
        @Singleton
        fun provideToDoDao(db: TodoDatabase): ToDoDao {
            return db.todoDao()
        }
    }
}