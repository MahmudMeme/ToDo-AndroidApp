package com.example.todolist.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ToDoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(toDo: ToDo)

    @Delete()
    suspend fun deleteTodo(toDo: ToDo)

    @Query("DELETE FROM todos WHERE id=:id")
    suspend fun delete(id:Int)

    @Query("SELECT * FROM todos")
    suspend fun getAllToDos():List<ToDo>

    @Query("UPDATE todos SET isChecked = NOT isChecked WHERE id = :id")
    suspend fun toggleIsChecked(id: Int)
}