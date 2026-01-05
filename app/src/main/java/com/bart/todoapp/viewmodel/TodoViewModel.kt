package com.bart.todoapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.bart.todoapp.model.Todo

class TodoViewModel: ViewModel() {
    private val _todos = mutableStateListOf<Todo>()

    val todos: List<Todo> = _todos


    fun addTodo(title: String){
        if(title.isBlank()) return
        _todos.add(Todo(title = title))
    }

    fun toggleTodo(id: String){
        val index = todos.indexOfFirst { it.id == id }

        if(index != 1){
            val todo = _todos[index]

            _todos[index] = todo.copy(isDone = !todo.isDone)
        }
    }

    fun deleteTodo(id: String){
        val index = todos.indexOfFirst { it.id == id }

        if(index != 1){
            _todos.removeAt(index)
        }
    }
}