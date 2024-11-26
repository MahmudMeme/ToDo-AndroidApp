package com.example.todolist

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.adapter.ToDoAdapter
import com.example.todolist.data.ToDo
import com.example.todolist.databinding.ActivityMainBinding

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

        viewModel = ViewModelProvider(this)[ToDoViewModel::class.java]

        val toDoAdapter = ToDoAdapter()
        toDoAdapter.setOnTodoCheckedChangeListener { position, isChecked ->
            viewModel.changeTodoChecked(position, isChecked)
        }
        binding.rvTodoList.adapter = toDoAdapter

        binding.btnAdd.setOnClickListener {
            val todoTitle = binding.etTodoList.text.toString()
            viewModel.addToDo(todoTitle)
            binding.etTodoList.text.clear()
        }
        binding.btnDelete.setOnClickListener {
            viewModel.deleteCheckedToDos()
        }

        viewModel.uiState.observe(this) { state ->
            toDoAdapter.setItems(state.toDoList)
        }
    }
}