package com.example.h1.domain.model

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val dueDate: String,
    val isDone: Boolean
)
