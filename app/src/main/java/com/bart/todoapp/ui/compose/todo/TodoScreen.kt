package com.bart.todoapp.ui.compose.todo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bart.todoapp.R
import com.bart.todoapp.data.TodoRepository
import com.bart.todoapp.ui.theme.TodoAppTheme
import com.bart.todoapp.viewmodel.TodoViewModel

@Composable
fun TodoScreen(
    todoRepository: TodoRepository,
    modifier: Modifier = Modifier
){
    // Create ViewModel with the repository
    val viewModel: TodoViewModel = viewModel(
        factory = TodoViewModel.Factory(todoRepository)
    )
    
    val todos = viewModel.todos

    Column(
        modifier = Modifier.fillMaxSize()
            .systemBarsPadding()
            .padding(16.dp)
            .then(modifier)
    ) {
        Text(
            text = stringResource(R.string.my_todos_title),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TodoInput(onAddTodo = viewModel::addTodo)

        TodoList(
            todos = todos,
            onToggle = viewModel::toggleTodo,
            onDelete = viewModel::deleteTodo
        )
    }
}

// Preview will need a mock repository
@Preview(showBackground = true)
@Composable
fun TodoScreenPreview(){
    // This is just for preview, won't be used in actual app
    TodoAppTheme {
        // Preview won't work properly with repository dependency
        // This is just a placeholder
    }
}
