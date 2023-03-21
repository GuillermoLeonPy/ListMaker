package com.raywenderlich.listmaker.ui.main

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

//constructor for the factory to receive an instance of SharedPreferences used to create MainViewModel
class MainViewModelFactory(private val sharedPreferences: SharedPreferences): ViewModelProvider.Factory {
    //The method returns an instance of MainViewModel that uses the SharedPreferences field within its constructor
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(sharedPreferences) as T
    }
}