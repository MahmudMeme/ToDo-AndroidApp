package com.example.todolist.data

interface LocalToDoDataSource {
    suspend fun insertToo(toDoEntity: ToDoEntity)
    suspend fun saveAll(list: List<ToDoEntity>)
    suspend fun delete(id: Int)
    suspend fun getAll(): List<ToDoEntity>
    suspend fun toggleChecked(id: Int)
    suspend fun deleteAllChecked(list: List<ToDoEntity>)
}