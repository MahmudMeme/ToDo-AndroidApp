package com.example.todolist.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.ToDo
import com.example.todolist.data.TodoRepository
import kotlinx.coroutines.launch

data class ToDoStateUi(
    val toDoList: List<ToDo> = emptyList(),
)

class ToDoViewModel(private val todoRepository: TodoRepository) : ViewModel() {
    //    private val _uiState = MutableLiveData<ToDoStateUi>(ToDoStateUi(listOf(ToDo(1, "firstItem"))))
    private val _uiState = MutableLiveData<ToDoStateUi>()
    val uiState: LiveData<ToDoStateUi> = _uiState


    init {
        viewModelScope.launch {
            loadData()
        }
    }

    //dodadov suspend
    suspend fun addToDo(toDo: String) {
        val state = _uiState.value ?: ToDoStateUi()


        if (toDo.isNotBlank()) {
            val newTodo = ToDo(0, toDo)
            todoRepository.insertToDo(newTodo)

//            _uiState.postValue(state.copy(toDoList = state.toDoList + ToDo(1,toDo)))
            _uiState.postValue(state.copy(todoRepository.getAllToDos()))
        }
    }

    suspend fun deleteCheckedToDos() {
        val state = _uiState.value ?: ToDoStateUi()
        todoRepository.deleteALLChecked(state.toDoList)
//        _uiState.postValue(state.copy(toDoList = state.toDoList.filter { !it.isChecked }))
        _uiState.postValue(state.copy(toDoList = todoRepository.getAllToDos()))
    }

    suspend fun changeTodoChecked(position: Int, checked: Boolean) {
        val state = _uiState.value ?: ToDoStateUi()
        val newList = state.toDoList.mapIndexed { index, toDo ->
            if (index == position) {
                toDo.copy(isChecked = checked)
            } else {
                toDo
            }
        }
        todoRepository.saveAll(newList)
//        _uiState.postValue(state.copy(toDoList = newList))
        _uiState.postValue(state.copy(toDoList = todoRepository.getAllToDos()))
    }

    private suspend fun loadData() {
        val list = todoRepository.getAllToDos()
        _uiState.value = ToDoStateUi(list)
    }
}