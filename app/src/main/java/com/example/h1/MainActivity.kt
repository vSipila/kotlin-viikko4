package com.example.h1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.h1.domain.Task
import com.example.h1.domain.mockTasks
import com.example.h1.domain.addTask
import com.example.h1.domain.toggleDone
import com.example.h1.domain.filterByDone
import com.example.h1.domain.sortByDueDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Tehtavalista()
            }
        }
    }
}

@Composable
fun Tehtavalista() {
    var tehtavat by remember { mutableStateOf(mockTasks) }
    var naytaValmiit by remember { mutableStateOf(false) }
    var jarjesta by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "TEHTÄVÄLISTA",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    val uusi = Task(
                        id = tehtavat.size + 1,
                        title = "Tehtävä ${tehtavat.size + 1}",
                        description = "kuvaus",
                        priority = 1,
                        dueDate = "28.2.2024",
                        done = false
                    )
                    tehtavat = addTask(tehtavat, uusi)
                }
            ) {
                Text("Lisää")
            }

            Button(
                onClick = {
                    naytaValmiit = !naytaValmiit
                }
            ) {
                Text(
                    if (naytaValmiit) "Näytä kaikki" else "Näytä valmiit"
                )
            }

            Button(
                onClick = {
                    jarjesta = !jarjesta
                }
            ) {
                Text(
                    if (jarjesta) "Perusjärjestys" else "Järjestä"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val naytettavat = if (naytaValmiit) {
            filterByDone(tehtavat, true)
        } else {
            tehtavat
        }

        val jarjestetyt = if (jarjesta) {
            sortByDueDate(naytettavat)
        } else {
            naytettavat
        }

        LazyColumn {
            items(jarjestetyt) { tehtava ->
                TehtavaRivi(tehtava = tehtava) {
                    tehtavat = toggleDone(tehtavat, tehtava.id)
                }
            }
        }
    }
}

@Composable
fun TehtavaRivi(tehtava: Task, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = onClick
        ) {
            Text(
                if (tehtava.done) "VALMIS" else "TEKEMÄTTÄ"
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(text = tehtava.title)
            Text(text = "Pvm: ${tehtava.dueDate}")
        }
    }
}