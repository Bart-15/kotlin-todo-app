package com.bart.todoapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.bart.todoapp.model.Todo
import com.bart.todoapp.proto.TodoListProto
import com.bart.todoapp.proto.TodoProto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.InputStream
import java.io.OutputStream

object TodoListSerializer : Serializer<TodoListProto> {
    override val defaultValue: TodoListProto = TodoListProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): TodoListProto {
        return TodoListProto.parseFrom(input)
    }

    override suspend fun writeTo(t: TodoListProto, output: OutputStream) {
        t.writeTo(output)
    }
}

private val Context.todoDataStore: DataStore<TodoListProto> by dataStore(
    fileName = "todo_list.pb",
    serializer = TodoListSerializer
)

class TodoRepository(private val context: Context) {
    
    // Convert from Proto to Domain model
    private fun TodoProto.toDomain(): Todo {
        return Todo(
            id = id,
            title = title,
            isDone = isDone
        )
    }
    
    // Convert from Domain to Proto model
    private fun Todo.toProto(): TodoProto {
        return TodoProto.newBuilder()
            .setId(id)
            .setTitle(title)
            .setIsDone(isDone)
            .build()
    }
    
    // Get all todos as a Flow
    val todos: Flow<List<Todo>> = context.todoDataStore.data
        .map { todoListProto ->
            todoListProto.todosList.map { it.toDomain() }
        }
    
    // Save all todos
    suspend fun saveTodos(todos: List<Todo>) {
        context.todoDataStore.updateData { _ ->
            TodoListProto.newBuilder()
                .addAllTodos(todos.map { it.toProto() })
                .build()
        }
    }
    
    // Add a single todo
    suspend fun addTodo(todo: Todo) {
        context.todoDataStore.updateData { currentList ->
            val currentTodos = currentList.todosList.toMutableList()
            currentTodos.add(todo.toProto())
            
            TodoListProto.newBuilder()
                .addAllTodos(currentTodos)
                .build()
        }
    }
    
    // Update a single todo
    suspend fun updateTodo(todo: Todo) {
        context.todoDataStore.updateData { currentList ->
            val currentTodos = currentList.todosList.toMutableList()
            val index = currentTodos.indexOfFirst { it.id == todo.id }
            
            if (index != -1) {
                currentTodos[index] = todo.toProto()
            }
            
            TodoListProto.newBuilder()
                .addAllTodos(currentTodos)
                .build()
        }
    }
    
    // Delete a todo by id
    suspend fun deleteTodo(id: String) {
        context.todoDataStore.updateData { currentList ->
            val filteredTodos = currentList.todosList.filter { it.id != id }
            
            TodoListProto.newBuilder()
                .addAllTodos(filteredTodos)
                .build()
        }
    }
}
