package com.example.todolist.data

import javax.inject.Inject

class TodoRepository @Inject constructor(
    private val localToDoDataSource: LocalToDoDataSource
) {

    suspend fun getAllToDos(): List<ToDoEntity> {
        return localToDoDataSource.getAll()
    }

    suspend fun insertToDo(toDoEntity: ToDoEntity) {
        localToDoDataSource.insertToo(toDoEntity)
    }

    suspend fun deleteTodo(id: Int) {
        localToDoDataSource.delete(id)
    }

    suspend fun toggleChecked(id: Int) {
        localToDoDataSource.toggleChecked(id)
    }

    suspend fun togglePinned(id: Int) {
        localToDoDataSource.togglePinned(id)
    }

    suspend fun saveAll(list: List<ToDoEntity>) {
        localToDoDataSource.saveAll(list)
    }

    suspend fun deleteALLChecked(list: List<ToDoEntity>) {
        localToDoDataSource.deleteAllChecked(list)
    }
}