package com.example.todolist.data

class RoomDataSource(private val toDoDao: ToDoDao) : LocalToDoDataSource {
    override suspend fun insertToo(toDo: ToDo) {
        toDoDao.insertTodo(toDo)
    }

    override suspend fun saveAll(list: List<ToDo>) {
        list.forEach { it -> toDoDao.insertTodo(it) }
    }

    override suspend fun delete(id: Int) {
        toDoDao.delete(id)
    }

    override suspend fun getAll(): List<ToDo> {
        return toDoDao.getAllToDos()
    }

    override suspend fun toggleChecked(id: Int) {
        toDoDao.toggleIsChecked(id)
    }

    override suspend fun deleteAllChecked(list: List<ToDo>) {
        for (todo in list) {
            if (todo.isChecked) {
                toDoDao.deleteTodo(todo)
            }
        }
    }
}