package com.example.todolist.data

interface LocalToDoDataSource {
    suspend fun insertToo(toDo: ToDo)
    suspend fun saveAll(list: List<ToDo>)
    suspend fun delete(id: Int)
    suspend fun getAll(): List<ToDo>
    suspend fun toggleChecked(id: Int)
    suspend fun deleteAllChecked(list: List<ToDo>)
}