package com.example.h1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.h1.ui.theme.H1Theme
import com.example.h1.view.*
import com.example.h1.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }

            H1Theme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()
                val viewModel: TaskViewModel = viewModel()
                val tasks by viewModel.tasks.collectAsState()

                NavHost(
                    navController = navController,
                    startDestination = ROUTE_HOME
                ) {
                    composable(ROUTE_HOME) {
                        HomeScreen(
                            tasks = tasks,
                            onAddTask = { title, desc, date ->
                                viewModel.addTask(title, desc, date)
                            },
                            onToggleTask = { taskId ->
                                viewModel.toggleTaskDone(taskId)
                            },
                            onUpdateTask = { taskId, title, desc, date ->
                                viewModel.updateTask(taskId, title, desc, date)
                            },
                            onRemoveTask = { taskId ->
                                viewModel.removeTask(taskId)
                            },
                            onFilterChange = { filterType ->
                                viewModel.filterByDone(filterType)
                            },
                            onSortByDate = {
                                viewModel.sortByDueDate()
                            },
                            onNavigateToCalendar = {
                                navController.navigate(ROUTE_CALENDAR)
                            },
                            onNavigateToSettings = {
                                navController.navigate(ROUTE_SETTINGS)
                            }
                        )
                    }

                    composable(ROUTE_CALENDAR) {
                        CalendarScreen(
                            tasks = tasks,
                            onToggleTask = { taskId ->
                                viewModel.toggleTaskDone(taskId)
                            },
                            onUpdateTask = { taskId, title, desc, date ->
                                viewModel.updateTask(taskId, title, desc, date)
                            },
                            onRemoveTask = { taskId ->
                                viewModel.removeTask(taskId)
                            },
                            onNavigateBack = {
                                navController.popBackStack()
                            }
                        )
                    }

                    composable(ROUTE_SETTINGS) {
                        SettingsScreen(
                            isDarkTheme = isDarkTheme,
                            onThemeChange = { isDarkTheme = it },
                            onNavigateBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}
