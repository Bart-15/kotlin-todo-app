package com.bart.todoapp.ui.compose.todo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bart.todoapp.viewmodel.TodoViewModel
import androidx.compose.ui.unit.dp
import com.bart.todoapp.R
import com.bart.todoapp.ui.theme.TodoAppTheme


@Composable
fun TodoInput(
    viewModel: TodoViewModel = viewModel()
){

    val keyboardController = LocalSoftwareKeyboardController.current

    var text by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        TextField(
            value = text,
            onValueChange = { text = it},
            singleLine = true,
            modifier = Modifier.weight(1f)
                .height(56.dp),
            placeholder = {
                Text(stringResource(R.string.add_task))
            },
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )

        Button(
            onClick = {
                viewModel.addTodo(text.trim())
                text = ""
                keyboardController?.hide()
            },
            enabled = text.isNotBlank(),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.height(56.dp)
        ){
            Text("Add")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun TodoInputPreview(){
    TodoAppTheme() {
        TodoInput()
    }
}

