package com.raywenderlich.listmaker.ui.detail

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.raywenderlich.listmaker.MainActivity
import com.raywenderlich.listmaker.R
import com.raywenderlich.listmaker.databinding.ListDetailActivityBinding
import com.raywenderlich.listmaker.models.TaskList
import com.raywenderlich.listmaker.ui.detail.ui.detail.ListDetailFragment
import com.raywenderlich.listmaker.ui.detail.ui.detail.ListDetailViewModel

class ListDetailActivity : AppCompatActivity() {

    lateinit var binding: ListDetailActivityBinding

    lateinit var viewModel: ListDetailViewModel
    lateinit var fragment: ListDetailFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ListDetailActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        viewModel = ViewModelProvider(this).get(ListDetailViewModel::class.java)
        //Use the key assigned to the list in MainActivity.kt to reference the list in the Intent and assign it to the list variable
        viewModel.list = intent.getParcelableExtra(MainActivity.INTENT_LIST_KEY)!!
        //Assign the title of the Activity to the name of the list to let the user know what list they’re viewing.
        title = viewModel.list.name
        binding.addTaskButton.setOnClickListener {
            showCreateTaskDialog()
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.detail_container, ListDetailFragment.newInstance())
                .commitNow()
        }
    }

    private fun showCreateTaskDialog() {
        val taskEditText = EditText(this)//Create an EditText so you can receive text input from the user
        taskEditText.inputType = InputType.TYPE_CLASS_TEXT
        AlertDialog.Builder(this).setTitle(R.string.task_to_add)
            .setView(taskEditText)
            .setPositiveButton(R.string.add_task){
                    dialog, _ ->
                    val task = taskEditText.text.toString()//to grab the text input and create a task from the input
                    viewModel.addTask(task)//notify the ViewModel a new item was added
                    //gives the ViewModel a chance to update the list and inform the Fragment to update the RecyclerView Adapter
                    dialog.dismiss()

            }.create().show()
    }

    //to run code whenever the back button is tapped to get back to MainActivity
    override fun onBackPressed() {
        val bundle = Bundle()
        //bundle up the list in its current state
        bundle.putParcelable(MainActivity.INTENT_LIST_KEY,viewModel.list)

        val intent = Intent()
        //put it into an Intent
        intent.putExtras(bundle)
        //set the result to RESULT_OK and pass in the Intent informing the Activity that everything happened according to plan
        setResult(Activity.RESULT_OK, intent)
        super.onBackPressed()
    }
}