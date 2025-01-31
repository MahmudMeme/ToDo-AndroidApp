package com.example.todolist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class ToDoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    var isChecked: Boolean = false,
    var isPinned : Boolean=false
)
