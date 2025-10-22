package org.ca1.studyapp.main

import android.app.Application
import org.ca1.studyapp.models.TaskMemStore
import timber.log.Timber


class MainApp : Application() {

    val tasks = TaskMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.i("Task started")
    }
}