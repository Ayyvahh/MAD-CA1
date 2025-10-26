package org.ca1.studyapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TaskModel(
    var id: Long = 0,
    var title: String = "",
    var description: String = "",
    var type: TaskType = TaskType.GENERAL,
    var completed: Boolean = false,
    var deadline: String = ""
) : Parcelable

enum class TaskType {
    ASSIGNMENT,
    LAB,
    STUDY,
    GENERAL
}
