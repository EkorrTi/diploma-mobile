package com.example.diploma.ui.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diploma.adapters.ContactsExpandableListAdapter
import com.example.diploma.adapters.ContactsRecyclerViewAdapter
import com.example.diploma.databinding.FragmentContactsBinding

class ContactsFragment : Fragment() {

    private var _binding: FragmentContactsBinding? = null
    private val viewModel: ContactsViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.get()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        val root: View = binding.root
//        val adapter = ContactsExpandableListAdapter(requireContext())
//        val expandableList: ExpandableListView = binding.contactsList
        return root
/*
//        viewModel.response.observe(viewLifecycleOwner) {
//            adapter.listDetail = it.dataMap
//            expandableList.setAdapter( adapter )
//            binding.contactsProgressBar.visibility = View.GONE
//            expandableList.visibility = View.VISIBLE
//        }
//        adapter.listDetail = viewModel.getData()
//        expandableList.setAdapter( adapter )
//        expandableList.setOnGroupExpandListener {
//            Toast.makeText(context,
//                    listTitle[it] + "List Expanded",
//                    Toast.LENGTH_SHORT).show()
//        }
//        expandableList.setOnChildClickListener { expandableListView, view, groupPosition, childPosition, id ->
//            Toast.makeText(context,
//                listTitle[groupPosition] + " -> " + listDetail[ listTitle[groupPosition] ]!![childPosition],
//                Toast.LENGTH_SHORT).show()
//            true
//        }
//        expandableList.visibility = View.VISIBLE */
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = ContactsRecyclerViewAdapter()
        val recyclerView = binding.contactsRecyclerView

        viewModel.response.observe(viewLifecycleOwner) {
            // put data into the adapter and set it after getting the response
            adapter.data = it
            recyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                this.adapter = adapter
            }

            // hide the loading icon and show the list
            binding.contactsProgressBar.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}