package com.example.h1

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

data class Task(
    val id: Int,
    var title: String,
    var done: Boolean,
    var dueDate: String
)

class TaskViewModel : ViewModel() {
    var tasks = mutableStateOf(listOf<Task>())
        private set

    init {
        tasks.value = listOf(
            Task(1, "Tee kotitehtävät", false, "2024-01-20"),
            Task(2, "Käy kaupassa", false, "2024-01-22"),
            Task(3, "Soita äidille", true, "2024-01-18")
        )
    }

    fun addTask(task: Task) {
        tasks.value = tasks.value + task
    }

    fun toggleDone(id: Int) {
        tasks.value = tasks.value.map { task ->
            if (task.id == id) task.copy(done = !task.done) else task
        }
    }

    fun removeTask(id: Int) {
        tasks.value = tasks.value.filter { it.id != id }
    }

    fun filterByDone(done: Boolean) {
        val allTasks = listOf(
            Task(1, "Tee kotitehtävät", false, "2024-01-20"),
            Task(2, "Käy kaupassa", false, "2024-01-22"),
            Task(3, "Soita äidille", true, "2024-01-18")
        )
        tasks.value = if (done) {
            allTasks.filter { it.done }
        } else {
            allTasks.filter { !it.done }
        }
    }

    fun sortByDueDate() {
        tasks.value = tasks.value.sortedBy { it.dueDate }
    }
}