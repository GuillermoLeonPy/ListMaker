package com.raywenderlich.listmaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.raywenderlich.listmaker.databinding.ActivityMainBinding
import com.raywenderlich.listmaker.models.TaskList
import com.raywenderlich.listmaker.ui.main.MainFragment
import com.raywenderlich.listmaker.ui.main.MainViewModel
import com.raywenderlich.listmaker.ui.main.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel//a property to hold the ViewModel
    private lateinit var binding: ActivityMainBinding//a property to store the binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //to create the ViewModel. ViewModelProvider: a store holding a reference to ViewModels for a particular Scope
        //scope: the lifetime of a ViewModel
        //How long that lifetime lasts depends on the UI component the ViewModel is attached to
        //see the use of this in the first parameter
        //ViewModels by default don't expect to have properties in their constructors
        //ViewModelFactory needs to be created and passed into ViewModelProvider
        //to understand how to construct ViewModels that require properties
        //factory accepts an instance of SharedPreferences
        viewModel = ViewModelProvider(this,
        MainViewModelFactory(PreferenceManager.getDefaultSharedPreferences(this))
        ).get(MainViewModel::class.java)//specifies what type of ViewModel should be retrieved from the Provider

        binding = ActivityMainBinding.inflate(layoutInflater)//acquire the binding for the Activity
        val view = binding.root
        setContentView(view)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        binding.fabButton.setOnClickListener {
            showCreateListDialog()
        }
    }

    private fun showCreateListDialog() {
        //Retrieve the strings from strings.xml
        val dialogTitle = getString(R.string.name_of_list)
        val positiveButtonTitle = getString(R.string.create_list)

        //AlertDialog.Builder to help construct the Dialog
        val builder = AlertDialog.Builder(this)
        val listTitleEditText = EditText(this)//to serve as the input field
        listTitleEditText.inputType = InputType.TYPE_CLASS_TEXT

        builder.setTitle(dialogTitle)
        builder.setView(listTitleEditText)//set the content View of the dialog

        builder.setPositiveButton(positiveButtonTitle) {
                dialog, _ -> dialog.dismiss()
            //to create a new list in MainViewModel
            viewModel.saveList(TaskList(listTitleEditText.text.toString()))
        }

        builder.create().show()
    }
}