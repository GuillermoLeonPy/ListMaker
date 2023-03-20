package com.raywenderlich.listmaker.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.listmaker.databinding.ListSelectionViewHolderBinding

//pass the type of ViewHolder you want the RecyclerView Adapter to use
class ListSelectionRecyclerViewAdapter: RecyclerView.Adapter<ListSelectionViewHolder>() {
//this class inherits from RecyclerView.Adapter, it needs to implement
//additional methods so it knows what to do when used in conjunction with a RecyclerView

    val listTitles = arrayOf("Shopping List", "Chores", "Android Tutorials")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSelectionViewHolder {
        //LayoutInflater is a system utility used to instantiate (or "inflate") a layout XML file (list_selection_view_holder.xml)
        // into its corresponding View objects
        val binding = ListSelectionViewHolderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        //a new binding class that allows you to bind data to the view
        return ListSelectionViewHolder(binding)
    }

    //determines how many items the RecyclerView has, the size of the array to match the size of the RecyclerView
    override fun getItemCount(): Int {
        //Adapter now knows how many items to display on the screen
        return listTitles.size
    }

    //Binding data to your ViewHolder
    //bind the titles to the ViewHolder at the right time depending on what position it has within the RecyclerView
    override fun onBindViewHolder(holder: ListSelectionViewHolder, position: Int) {//This is called repeatedly as you scroll through the RecyclerView
        //list_selection_view_holder.xml: the layout needed for the ViewHolder to display each item in the RecyclerView
        //*************************************it is required to Build the app to allow the view bindings to generate
        //declares TextView with id: itemNumber
        //declares TextView with id: itemString
        holder.binding.itemNumber.text = (position + 1).toString()
        holder.binding.itemString.text = listTitles[position]
    }
}