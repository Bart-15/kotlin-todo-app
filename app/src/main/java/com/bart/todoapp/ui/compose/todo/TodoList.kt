package com.bart.todoapp.ui.compose.todo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bart.todoapp.model.Todo
import com.bart.todoapp.ui.theme.TodoAppTheme

@Composable
fun TodoList(
    todos: List<Todo>,
    onToggle: (String) -> Unit,
    onDelete: (String) -> Unit
){
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = todos,
            key = { it.id }
        ){
            todo ->
            TodoItem(
                todo = todo,
                onDelete = { onDelete(todo.id) },
                onToggle = { onToggle(todo.id) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoListPreview(){
    TodoAppTheme() {
        TodoList(
            todos = listOf(
                Todo("1", "Wash the dishes"),
                Todo("1", "Build todo App", true)
        ),
            onToggle = {},
            onDelete = {}
        )
    }
}