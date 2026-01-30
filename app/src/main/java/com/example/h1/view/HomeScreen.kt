package com.example.h1

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen() {
    val viewModel: TaskViewModel = viewModel()
    val tasks by viewModel.tasks
    var newTaskTitle by remember { mutableStateOf("") }
    var newTaskDueDate by remember { mutableStateOf("") }
    var nextId by remember { mutableStateOf(4) }
    var showDialog by remember { mutableStateOf(false) }
    var selectedTask by remember { mutableStateOf<Task?>(null) }


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
                label = { Text("Määraaika") },
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
                                dueDate = newTaskDueDate.ifEmpty { "2026-01-25" }
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
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            Button(
                onClick = { viewModel.showAllTasks() },
                modifier = Modifier.weight(1f)
            ) {

                Text("Kaikki", fontSize = 12.sp)
            }
            Button(
                onClick = { viewModel.filterByDone(true) },
                modifier = Modifier.weight(1f)
            ) {

                Text("Suoritetut", fontSize = 12.sp)
            }
            Button(
                onClick = { viewModel.filterByDone(false) },
                modifier = Modifier.weight(1f)
            ) {

                Text("Tekemättömat", fontSize = 12.sp)
            }
            Button(
                onClick = { viewModel.sortByDueDate() },
                modifier = Modifier.weight(1f)
            ) {

                Text("Järjestä", fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(tasks) { task ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Checkbox(
                            checked = task.done,
                            onCheckedChange = { viewModel.toggleDone(task.id) }
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = task.title,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Määräaika: ${task.dueDate}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Button(
                            onClick = {
                                selectedTask = task
                                showDialog = true
                            },
                            modifier = Modifier.height(32.dp)
                        ) {

                            Text("Avaa", fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }

    if (showDialog && selectedTask != null) {
        DetailScreen(
            task = selectedTask!!,
            viewModel = viewModel,
            onClose = {
                showDialog = false
                selectedTask = null
            }
        )
    }
}