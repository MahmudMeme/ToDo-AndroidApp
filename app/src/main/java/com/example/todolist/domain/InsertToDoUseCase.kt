package com.example.todolist.domain

import com.example.todolist.data.ToDoEntity
import com.example.todolist.data.TodoRepository
import javax.inject.Inject

class InsertToDoUseCase @Inject constructor(
    private val repo: TodoRepository
) {
    suspend operator fun invoke(toDo: String) {
        if (toDo.isBlank()) return
        val newTodo = ToDoEntity(0, toDo)
        repo.insertToDo(newTodo)
    }
}