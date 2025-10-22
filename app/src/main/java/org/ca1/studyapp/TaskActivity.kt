package org.ca1.studyapp
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber


class TaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        Timber.plant(Timber.DebugTree())
        Timber.i("Task Activity started..")
    }
}