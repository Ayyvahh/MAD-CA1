package org.ca1.studyapp.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import org.ca1.studyapp.R
import org.ca1.studyapp.databinding.ActivityTaskBinding
import org.ca1.studyapp.main.MainApp
import org.ca1.studyapp.models.TaskModel
import org.ca1.studyapp.models.TaskType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


//TODO: JSON STORAGE
//TODO: ADD LIST SORTING BY TYPE/DEADLINE
class TaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTaskBinding
    var task = TaskModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var edit = false
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = "Add a Task"
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        if (intent.hasExtra("task_edit")) {
            edit = true
            task = intent.extras?.getParcelable("task_edit")!!
            binding.toolbarAdd.setTitle(R.string.edit_task)
            binding.taskTitle.setText(task.title)
            binding.taskDescription.setText(task.description)
            binding.btnAdd.setText(R.string.save_task)

            when (task.type) {
                TaskType.STUDY -> binding.chipStudy.isChecked = true
                TaskType.LAB -> binding.chipLab.isChecked = true
                TaskType.ASSIGNMENT -> binding.chipAssignment.isChecked = true
                TaskType.TASK -> binding.chipTask.isChecked = true
            }

            if (task.deadline.isNotBlank()) {
                binding.button.text = task.deadline
            }
        }

        binding.button.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select deadline date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

            // https://www.geeksforgeeks.org/kotlin/material-design-date-picker-in-android-using-kotlin/
            datePicker.addOnPositiveButtonClickListener { selection ->
                val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                val selectedDate = dateFormat.format(Date(selection))
                task.deadline = selectedDate
                binding.button.text = selectedDate
            }

            datePicker.show(supportFragmentManager, "DATE_PICKER")
        }

        binding.btnAdd.setOnClickListener {
            task.title = binding.taskTitle.text.toString().trim()
            task.description = binding.taskDescription.text.toString().trim()
            task.type = when {
                binding.chipStudy.isChecked -> TaskType.STUDY
                binding.chipLab.isChecked -> TaskType.LAB
                binding.chipAssignment.isChecked -> TaskType.ASSIGNMENT
                binding.chipTask.isChecked -> TaskType.TASK
                else -> TaskType.TASK
            }

            if (task.title.isEmpty()) {
                Snackbar.make(it, R.string.enter_task_title, Snackbar.LENGTH_LONG).show()
            } else if (task.title.length < 3) {
                Snackbar.make(it, "Task title must be at least 3 characters", Snackbar.LENGTH_LONG)
                    .show()
            } else if (task.title.length > 100) {
                Snackbar.make(it, "Task title must not exceed 100 characters", Snackbar.LENGTH_LONG)
                    .show()
            } else if (task.description.length > 300) {
                Snackbar.make(
                    it,
                    "Task description must not exceed 300 characters",
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                if (edit) {
                    app.tasks.update(task.copy())
                } else {
                    app.tasks.create(task.copy())
                }
                setResult(RESULT_OK)
                finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_task_activity, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}