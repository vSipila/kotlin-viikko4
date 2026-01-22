package com.example.h1

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen() {
    val viewModel: TaskViewModel = viewModel()
    val tasks by viewModel.tasks
    var newTaskTitle by remember { mutableStateOf("") }
    var newTaskDueDate by remember { mutableStateOf("") }
    var nextId by remember { mutableStateOf(4) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = newTaskTitle,
                onValueChange = { newTaskTitle = it },
                label = { Text("Tehtävä") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = newTaskDueDate,
                onValueChange = { newTaskDueDate = it },
                label = { Text("Määräaika") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (newTaskTitle.isNotBlank()) {
                        viewModel.addTask(
                            Task(
                                id = nextId,
                                title = newTaskTitle,
                                done = false,
                                dueDate = newTaskDueDate.ifEmpty { "2024-01-25" }
                            )
                        )
                        nextId++
                        newTaskTitle = ""
                        newTaskDueDate = ""
                    }
                }
            ) {
                Text("Lisää")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { viewModel.filterByDone(true) }) {
                Text("Suoritetut")
            }
            Button(onClick = { viewModel.filterByDone(false) }) {
                Text("Tekemättömät")
            }
            Button(onClick = { viewModel.sortByDueDate() }) {
                Text("Järjestä")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(tasks) { task ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = task.done,
                            onCheckedChange = { viewModel.toggleDone(task.id) }
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = task.title,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "Määräaika: ${task.dueDate}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        IconButton(onClick = { viewModel.removeTask(task.id) }) {
                            Text("Poista")
                        }
                    }
                }
            }
        }
    }
}