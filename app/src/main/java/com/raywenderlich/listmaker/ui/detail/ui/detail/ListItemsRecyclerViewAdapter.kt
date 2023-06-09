package com.raywenderlich.listmaker.ui.detail.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.listmaker.databinding.ListItemViewHolderBinding
import com.raywenderlich.listmaker.models.TaskList

//Adapter: give the RecyclerView the data
//to receive the data list it has to declare the reception in the constructor
class ListItemsRecyclerViewAdapter(var list: TaskList) : RecyclerView.Adapter<ListItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        //the layout needs to be created for the binding to be generated by Android Studio. Layout is declared by a .xml file in the res/layout folder
        val binding = ListItemViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListItemViewHolder(binding)
    }

    //tells the RecyclerView how many items to display
    override fun getItemCount(): Int {
        return list.tasks.size;//to show all of the tasks in your list returns the number of tasks it contains
    }

    //to hook up the data to the TextView
    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        //holder.binding. + [id of the element (with out _ (underscore)) in the list_item_view_holder.xml file] + .text
        holder.binding.textViewTask.text = list.tasks[position]
    }
}