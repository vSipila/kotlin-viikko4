package com.example.h1.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.h1.viewmodel.FilterType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    tasks: List<com.example.h1.domain.model.Task>,
    onAddTask: (String, String, String) -> Unit,
    onToggleTask: (Int) -> Unit,
    onUpdateTask: (Int, String, String, String) -> Unit,
    onRemoveTask: (Int) -> Unit,
    onFilterChange: (FilterType) -> Unit,
    onSortByDate: () -> Unit,
    onNavigateToCalendar: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var selectedTask by remember { mutableStateOf<com.example.h1.domain.model.Task?>(null) }
    var currentFilter by remember { mutableStateOf(FilterType.ALL) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tehtävälista") },
                actions = {
                    IconButton(onClick = onNavigateToCalendar) {
                        Icon(Icons.Default.DateRange, "Kalenteri")
                    }
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, "Asetukset")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, "Lisää tehtävä")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = currentFilter == FilterType.ALL,
                        onClick = {
                            currentFilter = FilterType.ALL
                            onFilterChange(FilterType.ALL)
                        },
                        label = { Text("Kaikki") }
                    )
                    FilterChip(
                        selected = currentFilter == FilterType.DONE,
                        onClick = {
                            currentFilter = FilterType.DONE
                            onFilterChange(FilterType.DONE)
                        },
                        label = { Text("Valmiit") }
                    )
                    FilterChip(
                        selected = currentFilter == FilterType.NOT_DONE,
                        onClick = {
                            currentFilter = FilterType.NOT_DONE
                            onFilterChange(FilterType.NOT_DONE)
                        },
                        label = { Text("Keskeneräiset") }
                    )
                }
                TextButton(onClick = onSortByDate) {
                    Text("Järjestä")
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                items(tasks) { task ->
                    TaskItem(
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

    if (showAddDialog) {
        TaskDialog(
            title = "Lisää tehtävä",
            onDismiss = { showAddDialog = false },
            onSave = { title, desc, date ->
                onAddTask(title, desc, date)
                showAddDialog = false
            }
        )
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
fun TaskItem(
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
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
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
                    style = MaterialTheme.typography.titleMedium
                )
                if (task.description.isNotEmpty()) {
                    Text(
                        text = task.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Text(
                    text = task.dueDate,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
fun TaskDialog(
    title: String,
    initialTitle: String = "",
    initialDescription: String = "",
    initialDueDate: String = "",
    onDismiss: () -> Unit,
    onSave: (String, String, String) -> Unit,
    onDelete: (() -> Unit)? = null
) {
    var taskTitle by remember { mutableStateOf(initialTitle) }
    var description by remember { mutableStateOf(initialDescription) }
    var dueDate by remember { mutableStateOf(initialDueDate) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column {
                OutlinedTextField(
                    value = taskTitle,
                    onValueChange = { taskTitle = it },
                    label = { Text("Otsikko") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Kuvaus") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = dueDate,
                    onValueChange = { dueDate = it },
                    label = { Text("Määräpäivä (YYYY-MM-DD)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (taskTitle.isNotEmpty()) {
                        onSave(taskTitle, description, dueDate)
                    }
                }
            ) {
                Text("Tallenna")
            }
        },
        dismissButton = {
            Row {
                if (onDelete != null) {
                    TextButton(onClick = onDelete) {
                        Text("Poista")
                    }
                }
                TextButton(onClick = onDismiss) {
                    Text("Peruuta")
                }
            }
        }
    )
}
