package com.bart.todoapp.ui.compose.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bart.todoapp.model.Todo
import com.bart.todoapp.ui.theme.TodoAppTheme

@Composable
fun TodoItem(
    modifier: Modifier = Modifier,
    todo: Todo,
    onToggle: () -> Unit,
    onDelete: () -> Unit,
){

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ){

        Checkbox(
            checked = todo.isDone,
            onCheckedChange = { onToggle() }
        )

        Text(
            text = todo.title,
            modifier = Modifier.weight(1f)
                .padding(start = 8.dp),
            textDecoration = if(todo.isDone) TextDecoration.LineThrough else TextDecoration.None
        )

        IconButton(onClick = onDelete, colors = IconButtonDefaults.iconButtonColors(
            contentColor = MaterialTheme.colorScheme.error
        )) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete"
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun TodoItemPreview(){
    TodoAppTheme() {
        TodoItem(
            todo = Todo("12", "Wash the dishes", true),
            onDelete = {},
            onToggle = {}
        )
    }
}


