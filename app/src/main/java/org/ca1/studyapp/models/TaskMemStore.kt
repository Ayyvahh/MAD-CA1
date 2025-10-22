package org.ca1.studyapp.models

import timber.log.Timber

class TaskMemStore : TaskStore {

    val tasks = ArrayList<TaskModel>()

    override fun findAll(): List<TaskModel> {
        return tasks
    }

    override fun create(task: TaskModel) {
        tasks.add(task)
        logAll()
    }

    fun logAll() {
        tasks.forEach { Timber.i("$it") }
    }
}

