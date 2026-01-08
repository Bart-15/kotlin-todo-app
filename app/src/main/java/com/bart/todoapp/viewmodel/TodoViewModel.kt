package com.bart.todoapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bart.todoapp.data.TodoRepository
import com.bart.todoapp.model.Todo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoViewModel(
    private val repository: TodoRepository
): ViewModel() {
    private val _todos = mutableStateListOf<Todo>()

    val todos: List<Todo> = _todos

    init {
        // Load todos from repository when ViewModel is created
        viewModelScope.launch {
            repository.todos
                .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
                .collect { persistedTodos ->
                    _todos.clear()
                    _todos.addAll(persistedTodos)
                }
        }
    }

    fun addTodo(title: String){
        if(title.isBlank()) return
        val newTodo = Todo(title = title)
        _todos.add(newTodo)

        // Persist the new todo
        viewModelScope.launch {
            repository.addTodo(newTodo)
        }
    }

    fun toggleTodo(id: String){
        val todoIndex = _todos.indexOfFirst { it.id == id }
        if (todoIndex != -1) {
            val todo = _todos[todoIndex]
            val updatedTodo = todo.copy(isDone = !todo.isDone)
            _todos[todoIndex] = updatedTodo

            // Persist the updated todo
            viewModelScope.launch {
                repository.updateTodo(updatedTodo)
            }
        }
    }

    fun deleteTodo(id: String){
        _todos.removeAll { it.id == id }

        // Delete from persistent storage
        viewModelScope.launch {
            repository.deleteTodo(id)
        }
    }

    // Factory to create the ViewModel with the repository
    class Factory(private val repository: TodoRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
                return TodoViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}