package com.example.todolist.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ToDoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(toDoEntity: ToDoEntity)

    @Delete()
    suspend fun deleteTodo(toDoEntity: ToDoEntity)

    @Query("DELETE FROM todos WHERE id=:id")
    suspend fun delete(id:Int)

    @Query("SELECT * FROM todos")
    suspend fun getAllToDos():List<ToDoEntity>

    @Query("UPDATE todos SET isChecked = NOT isChecked WHERE id = :id")
    suspend fun toggleIsChecked(id: Int)
}