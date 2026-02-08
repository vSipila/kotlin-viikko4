package com.example.h1.domain.model

data class Group(
    val id: Int,
    val name: String,
    val description: String = "",
    val members: List<Person> = emptyList()
)

data class Person(
    val name: String,
    val email: String
)