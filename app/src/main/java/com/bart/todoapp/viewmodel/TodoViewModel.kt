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
        _todos.replaceAll { todo ->
            if (todo.id == id) todo.copy(isDone = !todo.isDone)
            else todo
        }
    }

    fun deleteTodo(id: String){
        _todos.removeAll { it.id == id }
    }
}