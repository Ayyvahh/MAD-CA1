package org.ca1.studyapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.ca1.studyapp.databinding.CardTaskBinding
import org.ca1.studyapp.models.TaskModel

interface TaskListener {
    fun onTaskClick(task: TaskModel)
    fun onTaskCheckChanged(task: TaskModel, isChecked: Boolean)
    fun onTaskDelete(task: TaskModel)
}

class TaskAdapter (private var tasks: List<TaskModel>,
                               private val listener: TaskListener) :
    RecyclerView.Adapter<TaskAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardTaskBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val task = tasks[holder.bindingAdapterPosition]
        holder.bind(task, listener)
    }

    override fun getItemCount(): Int = tasks.size

    class MainHolder(private val binding : CardTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: TaskModel, listener: TaskListener) {
            binding.taskTitle.text = task.title
            binding.description.text = task.description
            binding.taskType.text = task.type.name
            binding.taskDone.isChecked = task.completed

            binding.root.setOnClickListener { listener.onTaskClick(task) }

            binding.taskDone.setOnCheckedChangeListener { _, isChecked ->
                listener.onTaskCheckChanged(task, isChecked)
            }

            binding.deleteButton.setOnClickListener { listener.onTaskDelete(task) }

            if (task.deadline.isNotBlank()) {
                binding.taskDeadline.visibility = View.VISIBLE
                binding.taskDeadline.text = "Due: ${task.deadline}"
            } else {
                binding.taskDeadline.visibility = View.GONE
            }
        }
    }
}