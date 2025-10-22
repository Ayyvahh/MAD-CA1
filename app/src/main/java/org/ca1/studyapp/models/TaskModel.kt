package org.ca1.studyapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TaskModel(
    var title: String = "",
    var description: String = ""
) : Parcelable
