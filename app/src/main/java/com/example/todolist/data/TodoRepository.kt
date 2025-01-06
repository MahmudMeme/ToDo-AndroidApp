package com.example.todolist.data

class TodoRepository(private val localToDoDataSource: LocalToDoDataSource) {

    suspend fun getAllToDos(): List<ToDo> {
        return localToDoDataSource.getAll()
    }

    suspend fun insertToDo(toDo: ToDo) {
        localToDoDataSource.insertToo(toDo)
    }

    suspend fun deleteTodo(id: Int) {
        localToDoDataSource.delete(id)
    }

    suspend fun toggleChecked(id: Int) {
        localToDoDataSource.toggleChecked(id)
    }

    suspend fun saveAll(list: List<ToDo>) {
        localToDoDataSource.saveAll(list)
    }

    suspend fun deleteALLChecked(list: List<ToDo>) {
        localToDoDataSource.deleteAllChecked(list)
    }
}