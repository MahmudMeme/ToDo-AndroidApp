package com.example.todolist

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.todolist.adapter.ToDoAdapter
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.viewModel.ToDoViewModel
import com.example.todolist.viewModel.ToDoViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ToDoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val viewModelFactory = ToDoViewModelFactory(this.applicationContext)
        viewModel = ViewModelProvider(this, viewModelFactory)[ToDoViewModel::class.java]

        val toDoAdapter = ToDoAdapter()
        toDoAdapter.setOnTodoCheckedChangeListener { position, isChecked ->
            lifecycleScope.launch {
                viewModel.changeTodoChecked(position, isChecked)
            }
        }
        binding.rvTodoList.adapter = toDoAdapter

        binding.btnAdd.setOnClickListener {
            val todoTitle = binding.etTodoList.text.toString()
            CoroutineScope(Dispatchers.Main).launch {
                viewModel.addToDo(todoTitle)
                binding.etTodoList.text.clear()
            }

        }
        binding.btnDelete.setOnClickListener {
            CoroutineScope(Dispatchers.Default).launch {
                viewModel.deleteCheckedToDos()
            }
        }

        viewModel.uiState.observe(this) { state ->
            toDoAdapter.setItems(state.toDoList)
        }
    }
}