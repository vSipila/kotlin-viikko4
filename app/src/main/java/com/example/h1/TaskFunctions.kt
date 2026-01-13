package com.example.h1.domain

fun addTask(tasklist: List<Task>, newTask: Task): List<Task> {
    return tasklist + newTask
}

fun toggleDone(taskList: List<Task>, id: Int): List<Task> {
    val uusilista = mutableListOf<Task>()

    for (tehtava in taskList) {
        if (tehtava.id == id) {
            val muokattu = Task(
                tehtava.id,
                tehtava.title,
                tehtava.description,
                tehtava.priority,
                tehtava.dueDate,
                !tehtava.done
            )
            uusilista.add(muokattu)
        } else {
            uusilista.add(tehtava)
        }
    }

    return uusilista
}

fun filterByDone(taskList: List<Task>, filterDone: Boolean): List<Task> {
    val uusilista = mutableListOf<Task>()

    for (tehtava in taskList) {
        if (tehtava.done == filterDone) {
            uusilista.add(tehtava)
        }
    }
    return uusilista
}



fun sortByDueDate(taskList: List<Task>): List<Task> {
    val uusiLista = mutableListOf<Task>()

    for (tehtava in taskList) {
    uusiLista.add(tehtava)

    }

    uusiLista.sortBy {it.dueDate}

    return uusiLista
}