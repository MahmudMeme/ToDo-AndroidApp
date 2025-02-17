package com.example.todolist.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.ToDoEntity
import com.example.todolist.data.TodoRepository
import com.example.todolist.domain.InsertToDoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ToDoStateUi(
    val toDoEntityList: List<ToDoEntity> = emptyList(),
)

@HiltViewModel
class ToDoViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
    private val insertToDoUseCase: InsertToDoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ToDoStateUi())
    val uiState = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            demoInsert()
            loadData()
        }
    }

    fun addToDo(toDo: String) {
        viewModelScope.launch {
            insertToDoUseCase(toDo)
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
        var id: Int = 0
        viewModelScope.launch {
            val newList = _uiState.value.toDoEntityList.mapIndexed { index, toDo ->
                if (index == position) {
                    id = toDo.id;
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

    private suspend fun demoInsert() {
        val list = todoRepository.getAllToDos()
        if (list.isEmpty()) {
            todoRepository.demoInsert()
        }
    }
}