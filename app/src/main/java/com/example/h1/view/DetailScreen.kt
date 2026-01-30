package com.example.h1

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun DetailScreen(
    task: Task,
    viewModel: TaskViewModel,
    onClose: () -> Unit
) {

    Dialog(onDismissRequest = onClose) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text("Muokkaa: ${task.title}")

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.updateTask(task.copy(title = task.title + "!"))
                        onClose()
                    }
                ) {

                    Text("Lisää huutomerkki")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        viewModel.removeTask(task.id)
                        onClose()
                    }
                ) {

                    Text("Poista tehtävä")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = onClose) {
                    Text("Sulje")
                }
            }
        }
    }
}