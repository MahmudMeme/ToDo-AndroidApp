package com.example.todolist.data

import javax.inject.Inject

class RoomDataSource @Inject constructor(private val toDoDao: ToDoDao) : LocalToDoDataSource {
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
//        if (toDoDao.getAllToDos().isEmpty()){
//            demoInsert();
//        }
        return toDoDao.getAllToDos()
    }

    override suspend fun toggleChecked(id: Int) {
        toDoDao.toggleIsChecked(id)
    }

    override suspend fun togglePinned(id: Int) {
        toDoDao.toggleIsPinned(id)
    }

    override suspend fun deleteAllChecked(list: List<ToDoEntity>) {
        for (todo in list) {
            if (todo.isChecked) {
                toDoDao.deleteTodo(todo)
            }
        }
    }

    override suspend fun demoInsert() {
        val toDo1= ToDoEntity(0,"you can insert new todo buy clicking on add button below",false,true)
        val toDo2= ToDoEntity(1,"you can pin a todo by clicking on a todo name")
        val toDo3= ToDoEntity(3,"you can check a todo by clicking on a check box for every todo")
        val toDo4= ToDoEntity(4,"checked todo",true)
        val toDo5= ToDoEntity(5,"you can delete all check todos by clicking on delete all button")

        toDoDao.insertTodo(toDo1)
        toDoDao.insertTodo(toDo2)
        toDoDao.insertTodo(toDo3)
        toDoDao.insertTodo(toDo4)
        toDoDao.insertTodo(toDo5)
    }
}