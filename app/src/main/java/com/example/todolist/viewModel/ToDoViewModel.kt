package com.example.todolist.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.ToDoEntity
import com.example.todolist.data.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ToDoStateUi(
    val toDoEntityList: List<ToDoEntity> = emptyList(),
)

class ToDoViewModel(private val todoRepository: TodoRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ToDoStateUi())
    val uiState = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            loadData()
        }
    }

    fun addToDo(toDo: String) {
        if (toDo.isBlank()) return

        viewModelScope.launch {
            val newTodo = ToDoEntity(0, toDo)
            todoRepository.insertToDo(newTodo)
            loadData()
        }
    }

    fun deleteCheckedToDos() {
        viewModelScope.launch {
            todoRepository.deleteALLChecked(_uiState.value.toDoEntityList)
            loadData()
        }
    }

    fun changeTodoChecked(position: Int, checked: Boolean) {
        viewModelScope.launch {
            val newList = _uiState.value.toDoEntityList.mapIndexed { index, toDo ->
                if (index == position) {
                    toDo.copy(isChecked = checked)
                } else {
                    toDo
                }
            }
            todoRepository.saveAll(newList)
            loadData()
        }
    }

    fun changeToDoPinned(position: Int) {
        var id : Int=0
        viewModelScope.launch {
            val newList = _uiState.value.toDoEntityList.mapIndexed { index, toDo ->
                if (index == position) {
                    id=toDo.id;
                    toDo.copy(isPinned = !toDo.isPinned)
                } else {
                    toDo
                }
            }
//            todoRepository.saveAll(newList)
            todoRepository.togglePinned(id)
            loadData()
        }
    }

    private suspend fun loadData() {
        val list = todoRepository.getAllToDos()
        _uiState.update { state ->
            state.copy(toDoEntityList = list)
        }
    }
}