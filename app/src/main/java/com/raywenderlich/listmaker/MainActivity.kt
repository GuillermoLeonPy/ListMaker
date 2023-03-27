package com.raywenderlich.listmaker

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.raywenderlich.listmaker.databinding.ActivityMainBinding
import com.raywenderlich.listmaker.models.TaskList
import com.raywenderlich.listmaker.ui.detail.ListDetailActivity
import com.raywenderlich.listmaker.ui.main.MainFragment
import com.raywenderlich.listmaker.ui.main.MainViewModel
import com.raywenderlich.listmaker.ui.main.MainViewModelFactory

//implments the interface MainFragmentInteractionListener to implement listItemTapped
class MainActivity : AppCompatActivity(), MainFragment.MainFragmentInteractionListener {

    private lateinit var viewModel: MainViewModel//a property to hold the ViewModel
    private lateinit var binding: ActivityMainBinding//a property to store the binding

    //This constant is used by the Intent to refer to a list whenever it needs to pass one to the new Activity
    companion object {
        const val INTENT_LIST_KEY = "list"
        const val LIST_DETAIL_REQUEST_CODE = 123
    }

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
            //as defined in the companion object in the MainFragment; the newInstance method
            val mainFragment = MainFragment.newInstance(this)
            supportFragmentManager.beginTransaction()
                .replace(R.id.detail_container, mainFragment)
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
            val taskList = TaskList(listTitleEditText.text.toString())
            viewModel.saveList(taskList)
            showListDetail(taskList)//the app passes that list to the new Activity
        }

        builder.create().show()
    }

    private fun showListDetail(list: TaskList) {
        //create an Intent and pass in the current Activity and class of the Activity you
        //want to show on the screen. Think of this as saying you’re currently on this
        //screen, now you want to move to that screen
        val listDetailIntent = Intent(this, ListDetailActivity::class.java)
        //Extras are keys with associated values
        listDetailIntent.putExtra(INTENT_LIST_KEY, list)
        //a method call to inform the current Activity to start another
        //Activity, making use of the information provided within the Intent
        //startActivity(listDetailIntent)

        //MainActivity.kt will wait to hear back from ListDetailActivity.kt
        //reporting back with the results when they’re finished.
        startActivityForResult(listDetailIntent,LIST_DETAIL_REQUEST_CODE)//a request code that lets you know which result you’re dealing with
    }

    //implement the interface method to pass the list to your method that creates the new Activity
    override fun listItemTapped(list: TaskList) {
        showListDetail(list)
    }

    //allows the Activity to receive the result of an Activity it starts
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //check the request code is the one you are expecting
        if (requestCode == LIST_DETAIL_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            //unwrap the data Intent passed in
            //It’s possible there isn’t any data at all here and it contains null
            //data?. allows to only execute a block of code if the variable .let is used on is not null
            data?.let {
                //Once you confirm there’s data, you save the list to MainViewModel
                viewModel.updateList(data.getParcelableExtra(INTENT_LIST_KEY)!!)
                viewModel.refreshLists()
            }
        }
    }
}