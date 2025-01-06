package com.example.todolist.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.data.RoomDataSource
import com.example.todolist.data.TodoDatabase
import com.example.todolist.data.TodoRepository

class ToDoViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(TodoRepository::class.java).newInstance(
            TodoRepository(
                RoomDataSource(TodoDatabase.getDatabase(context).todoDao())
            )
        )
    }
}