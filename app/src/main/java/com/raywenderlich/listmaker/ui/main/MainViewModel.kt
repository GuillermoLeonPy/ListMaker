package com.raywenderlich.listmaker.ui.main

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.raywenderlich.listmaker.models.TaskList

//constructor to store a SharedPreferences property to write key-value pairs to SharedPreferences
class MainViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {
    lateinit var onListAdded: (() -> Unit)//to inform other interested classes when a list is added to the app

    val lists: MutableList<TaskList> by lazy {//lazily created, until call the property, the property is empty
        retrieveLists()//populated by calling retrieveLists()
    }

    //gets all the saved TaskLists from SharedPreferences
    private fun retrieveLists(): MutableList<TaskList> {
        val sharedPreferencesContents = sharedPreferences.all
        val taskLists = ArrayList<TaskList>()

        for (taskList in sharedPreferencesContents) {
            val itemsHashSet = ArrayList(taskList.value as HashSet<String>)
            val list = TaskList(taskList.key, itemsHashSet)
            taskLists.add(list)
        }

        return taskLists
    }

    fun saveList(list: TaskList) {
        sharedPreferences.edit().putStringSet(list.name,list.tasks.toHashSet()).apply()//saved as a set of Strings
        lists.add(list)//updates the list's property
        onListAdded.invoke()//to let interested classes know about the new list
    }
}