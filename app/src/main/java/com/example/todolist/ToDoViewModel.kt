package com.example.todolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.data.ToDo

data class ToDoStateUi(
    val toDoList: List<ToDo> = emptyList(),
)

class ToDoViewModel : ViewModel() {
    private val _uiState = MutableLiveData<ToDoStateUi>(ToDoStateUi(listOf(ToDo("firstItem"))))
    val uiState: LiveData<ToDoStateUi> = _uiState

    fun addToDo(toDo: String) {
        val state = _uiState.value ?: ToDoStateUi()

        if (toDo.isNotBlank()) {
            _uiState.postValue(state.copy(toDoList = state.toDoList + ToDo(toDo)))
        }
    }

    fun deleteCheckedToDos() {
        val state = _uiState.value ?: ToDoStateUi()
        _uiState.postValue(state.copy(toDoList = state.toDoList.filter { !it.isChecked }))
    }

    fun changeTodoChecked(position: Int, checked: Boolean) {
        val state = _uiState.value ?: ToDoStateUi()
        val newList = state.toDoList.mapIndexed { index, toDo ->
            if (index == position) {
                toDo.copy(isChecked = checked)
            } else {
                toDo
            }
        }
        _uiState.postValue(state.copy(toDoList = newList))
    }
}