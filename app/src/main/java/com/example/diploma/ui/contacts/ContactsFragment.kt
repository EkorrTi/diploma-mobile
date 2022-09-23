package com.example.diploma.ui.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.diploma.adapters.ContactsExpandableListAdapter
import com.example.diploma.databinding.FragmentContactsBinding

class ContactsFragment : Fragment() {

    private var _binding: FragmentContactsBinding? = null
    val viewModel: ContactsViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val listTitle = viewModel.getData().keys.toList()
        val listDetail = viewModel.getData()

        val expandableList: ExpandableListView = binding.contactsList

        expandableList.setAdapter( ContactsExpandableListAdapter(requireContext(), listTitle, listDetail) )
        expandableList.setOnGroupExpandListener {
            Toast.makeText(context,
                    listTitle.get(it) + "List Expanded",
                    Toast.LENGTH_SHORT).show()
        }

        expandableList.setOnChildClickListener { expandableListView, view, i, i2, l ->
            Toast.makeText(context,
                listTitle[i] + " -> " + listDetail[ listTitle[i] ]!![i2],
                Toast.LENGTH_SHORT).show()
            true
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}