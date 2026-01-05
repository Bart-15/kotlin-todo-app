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
import com.bart.todoapp.ui.theme.TodoAppTheme
import com.bart.todoapp.viewmodel.TodoViewModel

@Composable
fun TodoScreen(
    viewModel: TodoViewModel = viewModel(),
    modifier: Modifier = Modifier
){

    Column(
        modifier = Modifier.fillMaxSize()
            .systemBarsPadding()
            .padding(16.dp)

    ) {
        Text(
            text = stringResource(R.string.my_todos_title),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TodoInput()
    }
}

@Preview(showBackground = true)
@Composable
fun TodoScreenPreview(){
    TodoAppTheme() {
        TodoScreen()
    }
}

