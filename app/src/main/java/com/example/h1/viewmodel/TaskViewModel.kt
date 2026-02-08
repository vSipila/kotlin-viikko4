package com.example.h1.viewmodel

import androidx.lifecycle.ViewModel
import com.example.h1.domain.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TaskViewModel : ViewModel() {
    private val _allTasks = MutableStateFlow<List<Task>>(emptyList())
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    private var nextId = 1
    private var currentFilter: FilterType = FilterType.ALL
    private var isSorted = false

    init {
        _allTasks.value = listOf(
            Task(nextId++, "Kaupassa käynti", "Maito, leipä, juusto", "2026-02-10", false),
            Task(nextId++, "Kooditehtävä", "Viikon 4 tehtävä", "2026-02-09", false),
            Task(nextId++, "Liikunta", "Sali 1h", "2026-02-08", true),
            Task(nextId++, "Siivous", "Imurointi ja pyykit", "2026-02-11", false),
            Task(nextId++, "Lukeminen", "Kirjan luku 5", "2026-02-08", true)
        )
        applyFilter()
    }

    fun addTask(title: String, description: String, dueDate: String) {
        val newTask = Task(
            id = nextId++,
            title = title,
            description = description,
            dueDate = dueDate,
            isDone = false
        )
        _allTasks.value = _allTasks.value + newTask
        applyFilter()
    }

    fun toggleTaskDone(taskId: Int) {
        _allTasks.value = _allTasks.value.map { task ->
            if (task.id == taskId) task.copy(isDone = !task.isDone)
            else task
        }
        applyFilter()
    }

    fun updateTask(taskId: Int, title: String, description: String, dueDate: String) {
        _allTasks.value = _allTasks.value.map { task ->
            if (task.id == taskId) {
                task.copy(
                    title = title,
                    description = description,
                    dueDate = dueDate
                )
            } else task
        }
        applyFilter()
    }

    fun removeTask(taskId: Int) {
        _allTasks.value = _allTasks.value.filter { it.id != taskId }
        applyFilter()
    }

    fun filterByDone(filterType: FilterType) {
        currentFilter = filterType
        applyFilter()
    }

    fun sortByDueDate() {
        isSorted = !isSorted
        applyFilter()
    }

    private fun applyFilter() {
        var filtered = when (currentFilter) {
            FilterType.ALL -> _allTasks.value
            FilterType.DONE -> _allTasks.value.filter { it.isDone }
            FilterType.NOT_DONE -> _allTasks.value.filter { !it.isDone }
        }

        if (isSorted) {
            filtered = filtered.sortedBy { it.dueDate }
        }

        _tasks.value = filtered
    }
}

enum class FilterType {
    ALL, DONE, NOT_DONE
}
