package com.example.h1.model

data class Task(
    val id: Int,
    var title: String,
    var done: Boolean,
    var dueDate: String
)