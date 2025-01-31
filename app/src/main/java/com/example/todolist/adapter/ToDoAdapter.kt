package com.example.todolist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.data.ToDoEntity
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.View
import com.example.todolist.databinding.TodoitemBinding


class ToDoAdapter : RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {
    private val data = mutableListOf<ToDoEntity>()
    private var onTodoCheckedChangeListener: ((position: Int, isChecked: Boolean) -> Unit)? = null
    private var onTodoPinClickListener: ((position: Int) -> Unit)? = null

    fun setOnTodoCheckedChangeListener(listener: (position: Int, isChecked: Boolean) -> Unit) {
        this.onTodoCheckedChangeListener = listener
    }
    fun setOnTodoPinClickListener(listener: (position: Int) -> Unit) {
        this.onTodoPinClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val binding = TodoitemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        val toDoViewHolder = ToDoViewHolder(binding)
        binding.cbtodoItem.setOnCheckedChangeListener { _, isChecked ->
            this.onTodoCheckedChangeListener?.invoke(
                toDoViewHolder.bindingAdapterPosition,
                isChecked
            )
        }
        binding.root.setOnClickListener {
            this.onTodoPinClickListener?.invoke(toDoViewHolder.bindingAdapterPosition)
        }
        return toDoViewHolder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        val currentItem = data[holder.bindingAdapterPosition]
        holder.bindItem(currentItem)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<ToDoEntity>) {
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    class ToDoViewHolder(private val binding: TodoitemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: ToDoEntity) {
            with(binding.tvtodoItem) {
                text = item.title
                paintFlags = if (item.isChecked) {
                    paintFlags or STRIKE_THRU_TEXT_FLAG
                } else {
                    paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
                }
            }
            binding.cbtodoItem.isChecked = item.isChecked
            binding.imageViewPin.visibility = if (item.isPinned) View.VISIBLE else View.GONE

        }
    }
}