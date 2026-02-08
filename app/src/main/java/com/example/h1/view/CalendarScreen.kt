package com.example.h1.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    tasks: List<com.example.h1.domain.model.Task>,
    onToggleTask: (Int) -> Unit,
    onUpdateTask: (Int, String, String, String) -> Unit,
    onRemoveTask: (Int) -> Unit,
    onNavigateBack: () -> Unit
) {
    var showEditDialog by remember { mutableStateOf(false) }
    var selectedTask by remember { mutableStateOf<com.example.h1.domain.model.Task?>(null) }

    val tasksByDate = tasks.groupBy { it.dueDate }.toSortedMap()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kalenteri") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Takaisin")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            tasksByDate.forEach { (date, tasksForDate) ->
                item {
                    Text(
                        text = date,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                items(tasksForDate) { task ->
                    CalendarTaskItem(
                        task = task,
                        onToggle = { onToggleTask(task.id) },
                        onEdit = {
                            selectedTask = task
                            showEditDialog = true
                        }
                    )
                }
            }
        }
    }

    if (showEditDialog && selectedTask != null) {
        TaskDialog(
            title = "Muokkaa tehtävää",
            initialTitle = selectedTask!!.title,
            initialDescription = selectedTask!!.description,
            initialDueDate = selectedTask!!.dueDate,
            onDismiss = { showEditDialog = false },
            onSave = { title, desc, date ->
                onUpdateTask(selectedTask!!.id, title, desc, date)
                showEditDialog = false
            },
            onDelete = {
                onRemoveTask(selectedTask!!.id)
                showEditDialog = false
            }
        )
    }
}

@Composable
fun CalendarTaskItem(
    task: com.example.h1.domain.model.Task,
    onToggle: () -> Unit,
    onEdit: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onEdit() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Checkbox(
                checked = task.isDone,
                onCheckedChange = { onToggle() }
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.bodyLarge
                )
                if (task.description.isNotEmpty()) {
                    Text(
                        text = task.description,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}
