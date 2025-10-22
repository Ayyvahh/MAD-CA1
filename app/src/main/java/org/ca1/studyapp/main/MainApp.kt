package org.ca1.studyapp.main

import android.app.Application
import org.ca1.studyapp.models.TaskModel
import timber.log.Timber


class MainApp : Application() {

    val tasks = ArrayList<TaskModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.i("Task started")
//
    }
}