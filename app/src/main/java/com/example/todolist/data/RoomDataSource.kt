package com.example.todolist.data

class RoomDataSource(private val toDoDao: ToDoDao) : LocalToDoDataSource {
    override suspend fun insertToo(toDoEntity: ToDoEntity) {
        toDoDao.insertTodo(toDoEntity)
    }

    override suspend fun saveAll(list: List<ToDoEntity>) {
        list.forEach { it -> toDoDao.insertTodo(it) }
    }

    override suspend fun delete(id: Int) {
        toDoDao.delete(id)
    }

    override suspend fun getAll(): List<ToDoEntity> {
        return toDoDao.getAllToDos()
    }

    override suspend fun toggleChecked(id: Int) {
        toDoDao.toggleIsChecked(id)
    }

    override suspend fun deleteAllChecked(list: List<ToDoEntity>) {
        for (todo in list) {
            if (todo.isChecked) {
                toDoDao.deleteTodo(todo)
            }
        }
    }
}