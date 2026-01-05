package com.bart.todoapp.model
import java.util.UUID

data class Todo(
    var id: String = UUID.randomUUID().toString(),
    val title: String,
    val isDone: Boolean = false
)