package com.example.h1

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

data class Task(
    val id: Int,
    var title: String,
    var done: Boolean,
    var dueDate: String
)

class TaskViewModel : ViewModel() {
    private val _tasks = mutableStateOf(listOf<Task>())
    private val _originalTasks = mutableStateOf(listOf<Task>())
    val tasks: State<List<Task>> = _tasks

    init {
        val initialTasks = listOf(
            Task(1, "Tee kotitehtavat", false, "2024-01-20"),
            Task(2, "Kay kaupassa", false, "2024-01-22"),
            Task(3, "Soita aidille", true, "2024-01-18")
        )
        _tasks.value = initialTasks
        _originalTasks.value = initialTasks
    }

    fun addTask(task: Task) {
        _tasks.value = _tasks.value + task
        _originalTasks.value = _originalTasks.value + task
    }

    fun toggleDone(id: Int) {
        _tasks.value = _tasks.value.map { task ->
            if (task.id == id) task.copy(done = !task.done) else task
        }
        _originalTasks.value = _originalTasks.value.map { task ->
            if (task.id == id) task.copy(done = !task.done) else task
        }
    }

    fun removeTask(id: Int) {
        _tasks.value = _tasks.value.filter { it.id != id }
        _originalTasks.value = _originalTasks.value.filter { it.id != id }
    }

    fun updateTask(updatedTask: Task) {
        _tasks.value = _tasks.value.map { task ->
            if (task.id == updatedTask.id) updatedTask else task
        }
        _originalTasks.value = _originalTasks.value.map { task ->
            if (task.id == updatedTask.id) updatedTask else task
        }
    }

    fun filterByDone(done: Boolean) {
        _tasks.value = if (done) {
            _originalTasks.value.filter { it.done }
        } else {
            _originalTasks.value.filter { !it.done }
        }
    }

    fun showAllTasks() {
        _tasks.value = _originalTasks.value
    }

    fun sortByDueDate() {
        _tasks.value = _tasks.value.sortedBy { it.dueDate }
        _originalTasks.value = _originalTasks.value.sortedBy { it.dueDate }
    }
}