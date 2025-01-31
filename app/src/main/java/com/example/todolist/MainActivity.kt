package com.example.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.todolist.adapter.ToDoAdapter
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.viewModel.ToDoViewModel
import com.example.todolist.viewModel.ToDoViewModelFactory
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
            viewModel.changeTodoChecked(position, isChecked)
        }

        toDoAdapter.setOnTodoPinClickListener { position ->
            viewModel.changeToDoPinned(position)
        }

        binding.rvTodoList.adapter = toDoAdapter

        binding.btnAdd.setOnClickListener {
            showAddTodoDialog()
//            val todoTitle = binding.etTodoList.text.toString()
//            viewModel.addToDo(todoTitle)
//            binding.etTodoList.text.clear()
        }
        binding.btnDelete.setOnClickListener {
            viewModel.deleteCheckedToDos()
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    toDoAdapter.setItems(state.toDoEntityList)
                }
            }
        }
    }

    private fun showAddTodoDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_todo, null)
        val etDialogTodo = dialogView.findViewById<EditText>(R.id.etDialogTodo)
        val btnDialogAdd = dialogView.findViewById<Button>(R.id.btnDialogAdd)
        val btnDialogCancel = dialogView.findViewById<Button>(R.id.btnDialogCancel)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        btnDialogAdd.setOnClickListener {
            val todoTitle = etDialogTodo.text.toString().trim()
            if (todoTitle.isNotEmpty()) {
                viewModel.addToDo(todoTitle)
                dialog.dismiss()
            } else {
                etDialogTodo.error = "Enter a todo"
            }
        }

        btnDialogCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

    }
}