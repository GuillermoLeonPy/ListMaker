package com.raywenderlich.listmaker.ui.detail.ui.detail

import androidx.lifecycle.ViewModel
import com.raywenderlich.listmaker.models.TaskList

//ViewModels serve one purpose: to manage the data that's shown
//With ViewModels, your data is kept separate from the Activity, no data loss worries
//in onCreate(). Acquire the ViewModel for the scope of your Activity. So long as the Activity remains, any data stored in the ViewModel will remain too
class ListDetailViewModel : ViewModel() {
    lateinit var onTaskAdded: (() -> Unit)//lambda: informs the Fragment when a new task is available
    lateinit var list: TaskList

    fun addTask(task: String) {
        list.tasks.add(task)
        onTaskAdded.invoke()//invokes the lambda
    }
}