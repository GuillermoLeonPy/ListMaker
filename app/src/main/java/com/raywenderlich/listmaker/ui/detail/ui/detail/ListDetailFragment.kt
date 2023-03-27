package com.raywenderlich.listmaker.ui.detail.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.raywenderlich.listmaker.databinding.ListDetailFragmentBinding

//import com.raywenderlich.listmaker.ui.detail.R

//Fragment: where your Views will be placed
class ListDetailFragment : Fragment() {

    lateinit var binding: ListDetailFragmentBinding//Android Studio generates a binding

    companion object {
        fun newInstance() = ListDetailFragment()
    }

    private lateinit var viewModel: ListDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //we want the ViewModel to be shared between the activity and Fragment
        viewModel = ViewModelProvider(requireActivity()).get(ListDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //we want the ViewModel to be shared between the activity and Fragment: requireActivity()
        viewModel = ViewModelProvider(requireActivity()).get(ListDetailViewModel::class.java)
        val recyclerAdapter = ListItemsRecyclerViewAdapter(viewModel.list)
        binding.listItemsRecyclerview.adapter = recyclerAdapter
        binding.listItemsRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        //callback to the lambda declared in ListDetailViewModel
        viewModel.onTaskAdded = {
            //you notify the adapter that the list of tasks has updated
            //causes the RecyclerView to be redrawn, showing any new items
            recyclerAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ListDetailFragmentBinding.inflate(inflater,container, false)//acquire the layout for the Fragment
        return binding.root//return the root of the View for your Fragment
    }

}